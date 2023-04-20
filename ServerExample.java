import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExample {
    public static  void main(String[] arg) {
        try (
            ServerSocket serverSocket = new ServerSocket(2000);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader clientIn = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            );
            BufferedReader keyboard = new BufferedReader(
                new InputStreamReader(System.in)
            );
        ) {
            // Communicate with the server
            String fromClient, toClient;
            System.out.print("Enter Something to START, type QUIT to end chat.\n(type) = ");
            toClient = keyboard.readLine();
            out.println(toClient);

            while ((fromClient = clientIn.readLine()) != null) {
                if (fromClient.equalsIgnoreCase("quit")) {
                    System.out.println("chat ended.");
                    break;
                }
                System.out.println("[Client]: " + fromClient);  
                StringBuilder t = new StringBuilder(fromClient);
                t.reverse();
                out.println(t.toString());
            }

        } catch (Exception exception) {
            System.err.println("[Error]: " + exception.getLocalizedMessage());
        }
    }
}
