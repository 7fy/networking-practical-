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
