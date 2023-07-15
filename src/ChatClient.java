import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;

   public ChatClient (String svAddress, int svPort) throws IOException {
       System.out.println("Trying to establish connection, please wait.");

       try {
           socket = new Socket(svAddress,svPort);
           System.out.println("Connected : " +  socket);

           socketStream();
       } catch (UnknownHostException ex) {
           System.exit(1);
       } catch (IOException ex) {
           System.exit(1);
       }
       String line = "";

       while (!line.equals("quit")) {
           try {
               line = input.readLine();

               output.write(line);
               output.newLine();
               output.flush();
           } catch (IOException ex) {
               System.out.println("Error: " + ex.getMessage());
           }
       }
       stop();
   }
   public void socketStream() throws IOException {
       input = new BufferedReader(new InputStreamReader(System.in));
       output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
   }
   public void stop() throws IOException {
       if(socket != null) {
           System.out.println("Close socket");
           socket.close();
       }
   }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.exit(1);
        }
        try {
            new ChatClient(args[0], Integer.parseInt(args[1]));
        } catch (NumberFormatException ex) {
            System.out.println("Wrong port " + args[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
