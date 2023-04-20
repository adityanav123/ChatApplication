// SERVER

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

class FeaturesInChat {
    static public String seeAvailableFeatures() {
        return "generate random number";
    }

    static String generateRandom() {
        return  String.valueOf((int)(Math.random() * Integer.MAX_VALUE + 10));
    }
}

class Server extends Thread {
    Socket socket;
    int clientId;

    public Server (Socket stk, int id) {
        socket = stk;
        clientId = id;
        System.out.println("[client connected.] id = " + clientId);
    }

    @Override
    public void run() {
        // Run client connection thread
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream writer = new PrintStream(socket.getOutputStream())
        ) {
            // first server will speak then client will start speaking.
            String welcomeMessage = "[Connected] Your client id = " + clientId + " ; Type 'quit' to exit the chat.";
            writer.println(welcomeMessage);

            String fromClient;

            while ((fromClient = reader.readLine()) != null) {
                if (fromClient.equalsIgnoreCase("quit")) {
                    System.out.println("client " + clientId + "; says bye.");
                    break;
                }

                if (fromClient.equalsIgnoreCase("/features")) {
                    String toClient = FeaturesInChat.seeAvailableFeatures();
                    writer.println(toClient + " => " + FeaturesInChat.generateRandom());
                } else {
                    System.out.println("[Client]: " + fromClient);
                    StringBuilder opStr = new StringBuilder(fromClient);
                    opStr.reverse();

                    // send the reversed string back.
                    writer.println(opStr);
                }
            }
        } catch (Exception ex) {
            System.err.println("[Error]: " + ex.getLocalizedMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Create Server socket and accept Connection
        try (ServerSocket serverSocket = new ServerSocket(2001)) {
            Socket socket; // to accept for any  client
            Server server;

            int clientcount = 0;

            // server always running
            while(true) {
                socket = serverSocket.accept(); // accept connection
                // create a thread
                server = new Server(socket, ++clientcount);
                server.start(); // starting the client thread communication
            }

        } catch (Exception exception) {
            System.err.println("[Error]: " + exception.getLocalizedMessage());
        }
    }
}