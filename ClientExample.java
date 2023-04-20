import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientExample {
    public static void main(String[] arg) {
        try (
            Socket clientSocket = new Socket("localhost", 2000);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader keybd = new BufferedReader(new InputStreamReader(System.in));
        ) {
            // Talk to Server
            String fromServer, toServer;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                System.out.print("(type) = ");
                toServer = keybd.readLine();
                if (toServer != null) {
                    System.out.println("Client: " + toServer);
                    out.println(toServer);
                }          
            }

            System.out.println("Server Closed, Chat Ended.");
        } catch (Exception exception) {
            System.err.println("[Error]: " + exception.getLocalizedMessage());
        }
    }
}
