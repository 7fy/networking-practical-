import java.io.BufferedWriter;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;

/**
 * Propose: record the clients's requests in a file.
 */
public class LogRequest {
    private String requestType;
    private String code;

    /**
     *Propose: the constructor of the function.
     *
     * @param requestType the request type
     * @param code the response code
     */
    public LogRequest(String requestType, String code) {
        this.requestType = requestType;
        this.code = code;
        contentToTxt(requestType, code);
    }

    /**
     *Propose: write the content in to a txt file.
     *
     * @param requestType the request type
     * @param code the response code
     */
    public void contentToTxt(String requestType, String code) {
        String str = new String(); //原有txt内容
        String s1 = new String(); //内容更新
        try {
            File f = new File("../log.txt");
            if (f.exists()) {
                System.out.print("file exist");
            } else {
                System.out.print("file does not exist");
                f.createNewFile(); // 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            input.close();
            String content = makeContent(requestType, code);
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            System.out.println("fuck");

        }
    }

    /**
     *Propose: build the content of the record.
     *
     * @param requestType the request type
     * @param code the response code
     * @return the content
     */
    public String makeContent(String requestType, String code) {
        String content;


        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        content = df.format(new Date()) + "\r\n";

        content += requestType + "\r\n";

        content += code + "\r\n";

        return content;


    }

}




