import java.io.*;
import java.net.*;
import java.net.UnknownHostException;

public class Client {
    public static void main(String args[]){

        DataInputStream input = null;
        DataOutputStream output = null;

        InetAddress inetaddress = null;
        int port = 1100;

        try{
            inetaddress = InetAddress.getByName("localhost");

            Socket socket = new Socket(inetaddress, port);

            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }

        catch (IOException exception) {
        }
    }

}
