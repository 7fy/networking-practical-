import java.io.IOException;

/**
 * Propose: a web server program that supports and responds basic HTTP requests from clients.
 *
 * @author 170011304
 * @version 1.1.7
 *
 */
public class WebServerMain {
    /**
     * Propose: the main function.
     * @param args input information
     * @throws IOException throws the errors from input streams and output streams
     */
    public static void main(String[] args) throws IOException {
        try {
            String path;
            int port;
            path = args[0];
            port = Integer.parseInt(args[1]);
            WebServer s = new WebServer(path, port);
        } catch (Exception e) {
            System.out.println("Usage: java WebServerMain <document_root> <port>");
        }
    }
}


