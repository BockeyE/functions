package hashCollision;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author bockey
 */
public class fileInsert {

    public static void main(String[] args) throws IOException {
        File f = new File("src/hashCollision/res.txt");
        // FileInputStream fis=new FileInputStream(f);
        FileWriter fos = new FileWriter(f, true);
        fos.write("{\r\n");

        System.out.println(f.getAbsolutePath());
        System.out.println(f.exists());

        ArrayList as = new ArrayList();
        StringBuilder sb;
        String[] ss = {"1", "2"};
//        String[] ss = {"atbUc6", "atc6bU", "bUatc6", "bUc6at", "c6bUat", "c6atbU", "c6bU", "atbU", "c6at", "bUat"};
        unrepeat(ss, fos);
        fos.write("\r\n}");
        fos.close();

    }

    public static void unrepeat(String[] ss, FileWriter fos) throws IOException {
        unrepeatSub(ss, 0, fos);
    }

    public static void unrepeatSub(String[] ss, int i, FileWriter fos) throws IOException {
        if (ss == null || i < 0 || i > ss.length) {
            return;
        }
        if (i == ss.length) {
            System.out.println(Arrays.toString(ss));
            System.out.println(ss.length);

            StringBuilder s1 = new StringBuilder("\"");
            for (String s : ss) {
                s1.append(s);
            }
            s1.append("\" : 0,\r\n");
            fos.write(s1.toString());
        } else {
            for (int j = i; j < ss.length; j++) {
                String temp = ss[j];
                ss[j] = ss[i];
                ss[i] = temp;
                unrepeatSub(ss, i + 1, fos);
                temp = ss[j];
                ss[j] = ss[i];
                ss[i] = temp;
            }
        }
    }
}
