import javax.swing.plaf.synth.SynthStyle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    public static void main(String[] arg) {
        try (Socket socket = new Socket("localhost", 2001);
             PrintStream writer = new PrintStream(socket.getOutputStream());
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // server speaks first then client starts speaking.
            String fromServer, toServer;
            while ((fromServer = reader.readLine()) != null) {
                System.out.println("[Server]: " + fromServer);
                toServer = keyboard.readLine();
                writer.println(toServer);
                if (toServer.equalsIgnoreCase("quit")) {
                    break;
                }
            }
            System.out.println("[Server Disconnected] chat ended.");
        } catch (Exception ex) {
            System.err.println("[Error]: " + ex.getLocalizedMessage());
        }
    }
}
