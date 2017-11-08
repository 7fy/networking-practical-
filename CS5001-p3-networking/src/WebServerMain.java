import java.io.IOException;

public class WebServerMain {
    public static void main(String args[]) throws IOException {
        try {
            String path;
            int port;
            path = args[0];
            port = Integer.parseInt(args[1]);
            WebServer s = new WebServer(path,port);
        } catch (Exception e) {
            System.out.println("Usage: java WebServerMAIN <document_root> <port>");
        }
    }
}


