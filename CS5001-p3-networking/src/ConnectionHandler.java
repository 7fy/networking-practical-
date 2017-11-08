import java.io.*;
import java.net.*;

public class ConnectionHandler {
    private Socket conn;       // socket representing TCP/IP connection to Client
    private InputStream is;    // get data from client on this input stream
    private OutputStream os;   // can send data back to the client on this output stream
    BufferedReader br;         // use buffered reader to read client data

    public ConnectionHandler(Socket conn) {
        this.conn = conn;
        try {
            is = conn.getInputStream();     // get data from client on this input stream
            os = conn.getOutputStream();  // to send data back to the client on this stream
            br = new BufferedReader(new InputStreamReader(is)); // use buffered reader to read client data
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler: " + ioe.getMessage());
        }
    }

    public void handleClientRequest(String path) {
        System.out.println("new ConnectionHandler constucted .... ");
        try {
            printClientData(path);
        } catch (Exception e) { // exit cleanly for any Exception (including IOException, ClientDisconnectedException)
            System.out.println("ConnectionHandler.handleClientRequest: " + e.getMessage());
            cleanup();     // cleanup and exit
        }
    }

    public String[] printClientData(String path) throws DisconnectedException, IOException {
        while (true) {
            String line = br.readLine(); // get data from client over socket
            String[] command = line.split("\\s");
            // response for the command
            // if readLine fails we can deduce here that the connection to the client is broken
            // and shut down the connection on this side cleanly by throwing a DisconnectedException
        // which will be passed up the call stack to the nearest handler (catch block)
        // in the run method
            ResponseClient rc = new ResponseClient(command, path);

        if (line == null || line.equals("null"))
//                    || line.equals(.exitString))  what is this ??
        {
            throw new DisconnectedException(" ... client has closed the connection ... ");
        }
        System.out.println("ConnectionHandler: " + command[0] + " " + command[1] + " " + command[2]);// assuming no exception, print out line received from client
//        return command;
    }
    }

    private void cleanup() {
        System.out.println("ConnectionHandler: ... cleaning up and exiting ... ");
        try {
            br.close();
            is.close();
            conn.close();
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
        }
    }

}
