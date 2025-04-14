// Echo Server
// Listens for incoming client connection, echoes messages back, and can handle multiple messages.

import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int MAX_THREADS = 10;

    public static void main(String[] args) {
        System.out.println("Basic Server, with echo functionality");

        // Use a port number between 1024 and 65535 for non-privileged applications.
        // Ports 0-1023 are reserved for system processes (privileged ports).
        // Setting port = 0 allows the OS to assign an available ephemeral port dynamically.
        int port = 8080;
        
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected to client, bound on port " + clientSocket.getLocalPort()
                    + clientSocket.getInetAddress());

            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Client says: " + inputLine);
                    out.println(inputLine);

                    if (inputLine.equalsIgnoreCase("exit")) {
                        break;
                    }
                }
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            }
        }
    }
}

