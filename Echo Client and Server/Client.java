// Echo Client
// Connects to the Echo Server, sends messages, and receives echo responses.
import java.net.Socket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException {

        System.out.println("Simple Echo Client");

        // Server IP Address and Port Number
        InetAddress serverAddress = InetAddress.getByName("localhost");
        int port = 8080;

        try (
                Socket socket = new Socket(serverAddress, port);  // Client socket
                BufferedReader userInput = new BufferedReader(
                        new InputStreamReader(System.in)); // Read user input
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Send text to server
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())) // Receive server text
        ) {
            System.out.println("Connected to server at " + serverAddress + ":" + port);
            System.out.println("Enter text (type 'exit' to quit): ");

            String text;
            while (true) {
                System.out.print("> ");  // Prompt for user input
                text = userInput.readLine();

                // Close connection if user types "exit"
                if (text.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection...");
                    break;
                }

                // Send input to server
                out.println(text);

                String response = in.readLine();  // Receive server response
                System.out.println("Server: " + response);
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
