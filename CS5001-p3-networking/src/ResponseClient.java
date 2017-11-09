import java.io.*;


public class ResponseClient {
    public String returnValue;

    public ResponseClient(String[] command, String path) throws IOException {
        String[] header = {"", "", ""};

        try {
            String directory = command[1];
            File file = new File(path + directory);

            if (!file.exists()) {
                header[0] = "404 Not Found";
                header[1] = "text/html";
                header[2] = String.valueOf(128);
                returnValue= command[2] + " " + header[0] + "\r\n" + "Server: Simple Java Http Server" + "\r\n" + "Content-Type: " + header[1] + "\r\n" + "Content-Length: " + header[2];
            } else if (file.exists()&&!command[0].equals("GET")&&!command[0].equals("HEAD")) {
                header[0] = "501 Not Implemented";
                header[1] = "text/html";
                header[2] = String.valueOf(128);
                returnValue= command[2] + " " + header[0] + "\r\n" + "Server: Simple Java Http Server" + "\r\n" + "Content-Type: " + header[1] + "\r\n" + "Content-Length: " + header[2];
            }else if(command[0].equals("GET")||command[0].equals("HEAD")){
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
                returnValue = returnValue(header, command, file);
            }
        } catch (Exception e) {
            System.out.println("fuck");
        }
    }

    public String returnValue(String[] header, String[] command, File file) throws IOException {

        String headerContent = command[2] + " " + header[0] + "\r\n" + "Server: Simple Java Http Server" + "\r\n" + "Content-Type: " + header[1] + "\r\n" + "Content-Length: " + header[2];
        String returnContent = new String();
        switch (command[0]) {

            case "GET":
                BufferedReader content;
                content = new BufferedReader(new FileReader(file));
                String c;
                String bodyContent = new String();
                while ((c = content.readLine()) != null) {
                    bodyContent += "\r\n" + c;
                }
//                FileInputStream bodyContent = new FileInputStream(file);
//                byte[] b = new byte[bodyContent.available()];
//                bodyContent.read(b);
//                bodyContent.close();
//                String buffer = new String(b, "UTF8");
////                System.out.println(buffer);
                returnContent = headerContent + "\r\n" +bodyContent;
                break;
            case "HEAD":
                returnContent = headerContent;
                break;
        }
        return returnContent;

    }
}
