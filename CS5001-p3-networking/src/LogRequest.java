import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogRequest {


    public void contentToTxt() {
        String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File("../log.txt");
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();

            String content = makeContent();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public String makeContent() {
        String content;
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        content = df.format(new Date());
        return content;
    }

}




