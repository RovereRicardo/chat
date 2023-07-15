import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private Socket clientSocket;
    private ServerSocket serverSocket;
    private BufferedReader input;

    public ChatServer(int port) {

        try {
            System.out.println("Binding port: " + port);
            serverSocket = new ServerSocket(port);

            System.out.println("Server started : " + serverSocket);

            System.out.println("Waiting for a connection.");
            clientSocket = serverSocket.accept();

            System.out.println("Client connected: " + clientSocket);

            while (true) {
                try {
                    String line = input.readLine();

                    if(line == null || line.equals("quit")) {
                        System.out.println("Client left.");
                        break;
                    }
                    System.out.println(line);
                } catch (IIOException ex) {
                    System.out.println("Error: " +  ex.getMessage());
                }
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } finally {
            close();
        }
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Java Chatserver [port]");
            System.exit(1);
        }
        try {
            new ChatServer(Integer.parseInt(args[0]));
        } catch (NumberFormatException e) {
            System.out.println("Wrong port " + args[0]);
        }
    }
    public void setupSocketStream() throws IOException {
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    }
    public void close() {
        try {
            if(clientSocket != null) {
                System.out.println("Closing connection");
                clientSocket.close();
            }
            if(serverSocket != null) {
                System.out.println("Closing server");
                serverSocket.close();
            }
        }catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
