import java.net.Socket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.net.SocketTimeoutException;

public class Client {
    private static final int TIMEOUT_MS = 5000; // 5 second timeout
    private static volatile boolean running = true;
    private static final Object lock = new Object(); // Add synchronization lock

    public static void main(String[] args) throws UnknownHostException {
        System.out.println("Simple Echo Client");

        InetAddress serverAddress = InetAddress.getByName("localhost");
        int port = 8080;

        ExecutorService executor = Executors.newFixedThreadPool(2); // Change to 2 threads

        try (Socket socket = new Socket(serverAddress, port)) {
            socket.setSoTimeout(TIMEOUT_MS);
            
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Connected to server at " + serverAddress + ":" + port);
            System.out.println("Enter text (type 'exit' to quit): ");

            // Start server response listener thread
            executor.submit(() -> listenForServerResponses(in));

            // Main thread handles user input
            while (running) {
                synchronized (lock) {
                    System.out.print("> ");
                    String text = userInput.readLine();

                    if (text == null || text.equalsIgnoreCase("exit")) {
                        running = false;
                        System.out.println("Closing connection...");
                        break;
                    }

                    out.println(text);
                    out.flush(); // Ensure the message is sent immediately
                }
            }

        } catch (SocketTimeoutException e) {
            System.err.println("Connection timed out: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            running = false;
            executor.shutdown();
            try {
                if (!executor.awaitTermination(2, TimeUnit.SECONDS)) { // Increased timeout
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
    }

    private static void listenForServerResponses(BufferedReader in) {
        try {
            String response;
            while (running && (response = in.readLine()) != null) {
                synchronized (lock) {
                    System.out.println("\nServer: " + response);
                    System.out.print("> "); // Reprint prompt
                }
            }
        } catch (SocketTimeoutException e) {
            if (running) {
                System.err.println("\nServer response timeout: " + e.getMessage());
            }
        } catch (IOException e) {
            if (running) {
                System.err.println("\nError reading from server: " + e.getMessage());
            }
        }
    }
}