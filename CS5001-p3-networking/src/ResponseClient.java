import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ResponseClient {
    public String head;
    public byte[] body;

    public ResponseClient(String[] command, String path) throws IOException {

        try {
            String directory = command[1];
            File file = new File(path + directory);
            head = returnHeader(command, file);
            body = returnBody(command, file);

//            System.out.println(head);
        } catch (Exception e) {
            System.out.println("Usage: java WebServerMain <document_root> <port>");
        }
    }

    public String returnHeader(String[] command, File file) throws IOException {
        String[] header = {"", "", ""};
        if (!file.exists()) {
            header[0] = "404 Not Found";
            header[1] = "text/html";
            header[2] = String.valueOf(128);
            head = command[2] + " " + header[0] + "\r\n" + "Server: Simple Java Http Server" + "\r\n" + "Content-Type: " + header[1] + "\r\n" + "Content-Length: " + header[2] + "\r\n\r\n";
        } else if (file.exists() && !command[0].equals("GET") && !command[0].equals("HEAD") && !command[0].equals("DELETE") && !command[0].equals("TRACE") && !command[0].equals("OPTIONS")) {
            header[0] = "501 Not Implemented";
            header[1] = "text/html";
            header[2] = String.valueOf(128);
            head = command[2] + " " + header[0] + "\r\n" + "Server: Simple Java Http Server" + "\r\n" + "Content-Type: " + header[1] + "\r\n" + "Content-Length: " + header[2] + "\r\n\r\n";
        } else if (command[0].equals("GET") || command[0].equals("HEAD")) {
            header[0] = "200 OK";
            String fileName = file.getName();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (fileType.equals("html")) {
                header[1] = "text/html";
            } else if (fileType.equals("txt")) {
                header[1] = "text/plain";
            } else if (fileType.equals("jpg")) {
                header[1] = "image/jpeg";
            } else if (fileType.equals("gif")) {
                header[1] = "image/gif";
            } else if (fileType.equals("png")) {
                header[1] = "image/png";
            } else {
                header[1] = "others";
            }
            header[2] = String.valueOf(file.length());
            head = command[2] + " " + header[0] + "\r\n" + "Server: Simple Java Http Server" + "\r\n" + "Content-Type: " + header[1] + "\r\n" + "Content-Length: " + header[2] + "\r\n\r\n";
        }
        return head;
    }

    public byte[] returnBody(String[] command, File file) throws IOException {


        switch (command[0]) {

            case "GET":
/*                BufferedReader content;
                content = new BufferedReader(new FileReader(file));
                String c;
                String bodyContent = new String();
                while ((c = content.readLine()) != null) {
                    bodyContent += "\r\n" + c;
                }*/
                FileInputStream bodyContent = new FileInputStream(file);
                byte[] b = new byte[bodyContent.available()];
                bodyContent.read(b);
                bodyContent.close();
                body = b;
                break;
            case "HEAD":
                body = null;
                break;
            case "DELETE":
                file.delete();
                body = null;
                break;
            case "TRACE":
                String bodyTrace = command[0] + " " + command[1] + " " + command[2] + " ";
                body = bodyTrace.getBytes();
                break;
            case "OPTIONS":
                String bodyOptions = "ALLOW" + "\r\n" + "GET: " + "\r\n" + "HEAD: " + "\r\n" + "DELETE: " + "\r\n" + "TRACE: " + "\r\n" + "OPTIONS: " + "\r\n";
                System.out.println(bodyOptions);
                body = bodyOptions.getBytes();
                break;
        }

//        System.out.println(body);
        return body;
    }


}
