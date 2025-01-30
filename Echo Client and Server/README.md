# Echo Server and Client

This project demonstrates a basic **Echo Server** and **Echo Client** implementation using Java's `Socket` programming. The server listens for incoming connections, echoes back received messages, and can handle multiple client messages.

## Project Structure

- **Main.java**: The server listens for incoming connections and echoes messages.
- **Client.java**: The client connects to the server, sends messages, and receives echoed responses.

## How to Run

### Start the Server
1. Open a terminal and navigate to the directory containing `Main.java`.
2. Compile the server:
    javac Main.java
3. Run the server:
    java Main
   The server will listen on port **8080** by default.

### Start the Client
1. Open another terminal and navigate to the directory containing `Client.java`.
2. Compile the client:
    javac Client.java
3. Run the client:
    java Client

### Communication
- The client will prompt for input; type your message and press **Enter**.
- The server will echo your message back.
- Type **"exit"** in the client to terminate the connection.


## Notes
- The client and server are both using **localhost** as the server address. Change the address if testing over a network.

