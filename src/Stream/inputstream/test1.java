package Stream.inputstream;

import java.io.*;

/**
 * @author bockey
 */
public class test1 {


    public static void main(String[] args) throws IOException {
        sss();
    }
    /*
    一个流ips只能读一次，为了解决这个问题需要用到mark
     */
    public static void sss() throws IOException {
        File f = new File("C:\\Users\\user1\\Desktop\\bh2.jpg");
        InputStream ips = new FileInputStream(f);

        BufferedInputStream bis = new BufferedInputStream(ips, 1024 * 1024 * 50);
        bis.mark(1024 * 1024 * 50);

        System.out.println(bis.markSupported());
        FileOutputStream ops = new FileOutputStream("C:\\Users\\user1\\Desktop\\auto\\2ss.jpg");
        FileOutputStream ops2 = new FileOutputStream("C:\\Users\\user1\\Desktop\\auto\\3ss.jpg");
        int read = 0;
        byte[] tem = new byte[1024 * 10];
        while ((read = bis.read(tem)) != -1) {
            ops.write(tem, 0, read);
        }
        read = 0;
        bis.reset();
        while ((read = bis.read(tem)) != -1) {
            ops2.write(tem, 0, read);
        }

        ops.flush();
        ops2.flush();
        ops2.close();
        ops.close();
    }
}
