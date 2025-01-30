// Basic Http Server

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

class Main{

    public static void main(String args[]){

        // Create an object port of any value between 1025 and 65536
        // to avoid collisions with priviledged services running on ports 0 to 1024
        int port = 1099;
       // InetAddress = "http://localhost";

        try{

            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(port);

            // Create socket to accept client connections
            Socket socket = serverSocket.accept();


            // Handle data of incoming connections
            DataInputStream dataInputStream = new DataInputStream(
                    socket.getInputStream());

            // Handle data of outgoing connections
            DataOutputStream dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());

        }
        catch (IOException ex) {

        }
    }
}