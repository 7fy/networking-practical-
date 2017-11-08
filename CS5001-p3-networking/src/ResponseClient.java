import java.io.*;

public class ResponseClient {




    public ResponseClient(String[] command, String path) throws IOException {
        String[] header = {"","",""};
          System.out.println(command[0]);

        try {
            String directory = command[1];
            File file = new File(path+directory);
            System.out.println(directory);

            if (!file.exists()) {
                header[0] = "404 Not Found";
            } else {
                header[0] = "200 OK";

                String fileName = file.getName();
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
                if(fileType == "html") {
                    header[1] = "text/html";
                }else if(fileType =="txt") {
                    header[1] = "text/plain";
                }else if(fileType == "jpg") {
                    header[1] = "image/jpeg";
                }else if(fileType == "gif") {
                    header[1] = "image/gif";
                }else if(fileType == "png") {
                    header[1] = "image/png";
                }else{
                    header[1] = "others";
                }


                header[2] = String.valueOf(file.length());
                String headerContent = command[2]+" "+header[0]+"\r\n"+"Server: Simple Java Http Server"+"\r\n"+"Content-Type:"+" "+header[1]+"\r\n"+"Content-Length"+header[2];
                System.out.print(headerContent);
            }
            System.out.println(header[0]);
            switch (command[0]) {
                case "GET":
                    BufferedReader content;
                    content = new BufferedReader(new FileReader(file));
                    int c;
                    c = content.read();
                    System.out.println(c);
                    break;
                case "HEAD":
                    break;
            }

        } catch (Exception e) {
            System.out.println("fuck");
        }
    }




}
