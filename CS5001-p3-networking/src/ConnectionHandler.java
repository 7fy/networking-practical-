import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private Socket conn;       // socket representing TCP/IP connection to Client
    private String path;
    private InputStream is;    // get data from client on this input stream
    private BufferedReader br;         // use buffered reader to read client data
    private BufferedOutputStream bos;

    public ConnectionHandler(Socket conn, String path) {
        this.conn = conn;
        this.path = path;
        try {
            is = conn.getInputStream();     // get data from client on this input stream
            br = new BufferedReader(new InputStreamReader(is)); // use buffered reader to read client data
            bos = new BufferedOutputStream(conn.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler: " + ioe.getMessage());
        }
    }

    public void run() { // run method is invoked when the Thread's start method (ch.start(); in Server class) is invoked
        System.out.println("new ConnectionHandler thread started .... ");
        try {
            printClientData(path);
        } catch (Exception e) { // exit cleanly for any Exception (including IOException, ClientDisconnectedException)
            System.out.println("ConnectionHandler:run " + e.getMessage());
            cleanup();     // cleanup and exit
        }
    }
//
//
//    public void handleClientRequest(String path) {
//        System.out.println("new ConnectionHandler constucted .... ");
//        try {
//            printClientData(path);
//        } catch (Exception e) { // exit cleanly for any Exception (including IOException, ClientDisconnectedException)
//            System.out.println("ConnectionHandler.handleClientRequest: " + e.getMessage());
//            cleanup();     // cleanup and exit
//        }
//    }

    public void printClientData(String path) throws DisconnectedException, IOException {

        String line = br.readLine(); // get data from client over socket
        String[] command = line.split("\\s");
        // response for the command
        // if readLine fails we can deduce here that the connection to the client is broken
        // and shut down the connection on this side cleanly by throwing a DisconnectedException
        // which will be passed up the call stack to the nearest handler (catch block)
        // in the run method
        ResponseClient rc = new ResponseClient(command, path);
        byte[] c = rc.head.getBytes("UTF8");
        bos.write(c);

        //THIS IS FOR RETURN BODY, IF THE COMMAND DO NOT NEED ME TO RETURN CONTENT, DO NOT RETURN BODY
        String directory = command[1];
        File file = new File(path + directory);
        if (file.exists() && command[0].equals("GET")) {
            bos.write(rc.body);
        }

        bos.flush();
        conn.close();
        bos.close();
        String t = new String(rc.body);
        LogRequest log = new LogRequest(rc.head, t);

    }


    private void cleanup() {
        System.out.println("ConnectionHandler: ... cleaning up and exiting ... ");
        try {
            br.close();
            is.close();
//            bos.flush();
//            conn.close();
//            bos.close();
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
        }
    }

}
