// Echo Server
// Listens for incoming client connection, echoes messages back, and can handle multiple messages.

import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {

        System.out.println("Basic Server, with echo functionality");

        // Use a port number between 1024 and 65535 for non-privileged applications.
        // Ports 0-1023 are reserved for system processes (privileged ports).
        // Setting port = 0 allows the OS to assign an available ephemeral port dynamically.
        int port = 8080;

        ServerSocket serverSocket;
        Socket socket;

        try {

            // Create a server socket
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for connection...");

            while (true) {
                socket = serverSocket.accept();

                // display port server is running on
                System.out.println("Connected to client, bound on port " + serverSocket.getLocalPort()
                        + socket.getInetAddress());

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    System.out.println("Client says: " +
                            inputLine);

                    // Echo received message back to client
                    out.println(inputLine);

                    if (inputLine.equalsIgnoreCase("exit")){
                        break;
                    }
                }

                // terminate connection with client
                socket.close();
                System.out.println("Client disconnected.");

            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}