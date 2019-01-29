package hashCollision;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bockey
 */
public class create {

    public static void main(String[] args) throws IOException {
        File f = new File("src/hashCollision/res.txt");
        // FileInputStream fis=new FileInputStream(f);
        FileWriter fos = new FileWriter(f, true);
        fos.write("{");
        System.out.println(f.getAbsolutePath());
        System.out.println(f.exists());
        ArrayList as = new ArrayList();
        StringBuilder sb;
        String[] arr = {"at", "bU", "c6"};
        for (int i = 0; i < 16; i++) {
            sb = new StringBuilder("\"");

            for (int k = 0; k <= i; k++) {
                sb.append(arr[0]);
            }
            for (int k = 0; k < 100 - i; k++) {
                sb.append(arr[1]);
            }
            sb.append("\" : 0,\r\n");
            fos.write(sb.toString());
            sb = null;
        }
        System.out.println(as);
        fos.write("\r\n}");
        fos.close();

    }

    public String combo(int n, String[] arr, StringBuilder tem, List rtn) {
        if (n == 1) {
            for (int i = 0; i < 3; i++) {
                tem.append(arr[i]);
            }
        } else {

        }
        return null;
    }

}
