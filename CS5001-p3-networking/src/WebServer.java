import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/*



 */
public class WebServer {
    private ServerSocket ss;
    private String path;
    private int port;

    public WebServer(String path, int port) throws IOException, DisconnectedException {
        this.path = path;
        this.port = port;
        runServer();
    }


//    public void runServer() throws IOException, DisconnectedException {
//        ss = new ServerSocket(port);
//        Socket conn = ss.accept();
//        System.out.println("Server got new connection request from " + conn.getInetAddress());
//        ConnectionHandler ch = new ConnectionHandler(conn); // create new handler for the connection
////        ch.handleClientRequest(path);                           // handle the client request
//        ch.start();
//    }

    public void runServer() throws IOException, DisconnectedException {
        ss = new ServerSocket(port);
        int i = 0;
        while (i <= 10) {
            Socket conn = ss.accept(); // will wait until client requests a connection, then returns connection (socket)
            System.out.println("Server got new connection request from " + conn.getInetAddress());
            ConnectionHandler ch = new ConnectionHandler(conn, path); // create new handler for this connection
            ch.start();
            i++;
            // start handler thread
        }

//        Socket conn = ss.accept();
//        System.out.println("Server got new connection request from " + conn.getInetAddress());
//        ConnectionHandler ch = new ConnectionHandler(conn); // create new handler for the connection
////        ch.handleClientRequest(path);                           // handle the client request
//        ch.start();
    }


}

