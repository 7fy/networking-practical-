
import java.io.*;
import java.net.*;


public class WebServer {
    private ServerSocket ss;
    private String path;
    private int port;

    public WebServer(String path, int port) throws IOException, DisconnectedException {
        this.path = path;
        this.port = port;
        runServer();
    }


    public void runServer() throws IOException, DisconnectedException {
        ss = new ServerSocket(port);
        Socket conn = ss.accept();
        System.out.println("Server got new connection request from " + conn.getInetAddress());
        ConnectionHandler ch = new ConnectionHandler(conn); // create new handler for the connection
        ch.handleClientRequest(path);                           // handle the client request
        ss.close();
    }


}

