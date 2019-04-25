package AAAAAAPracs.CodeAmountCount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CalculateRows {

    static long classCount = 0;
    static long emptyLines = 0;
    static long commentLines = 0;
    static long writtenLines = 0;
    static long allLines = 0;

    public static void main(String[] args) throws Exception {
        File f = new File("C:\\ZZBK\\work\\JAVA");
        String type = ".java";
        CalculateRows.treeFile(f, type);
        System.out.println("路径：" + f.getPath());
        System.out.println(type + "文件数量：" + classCount);
        System.out.println("代码数量：" + writtenLines);
        System.out.println("注释数量：" + commentLines);
        System.out.println("空行数量：" + emptyLines);
        if (classCount == 0) {
            System.out.println("代码平均数量:" + 0);
        } else {
            System.out.println("代码平均数量:" + writtenLines / classCount);
        }
        System.out.println("总 行数量：" + allLines);
    }

    public static void treeFile(File f, String type) throws Exception {
        File[] childs = f.listFiles();

        String[] doubleNotes = doubleNoteType(type);
        String singleNotes = singleNoteType(type);
        for (int i = 0; i < childs.length; i++) {
            File file = childs[i];
            if (!file.isDirectory()) {
                if (file.getName().endsWith(type)) {
                    classCount++;
                    BufferedReader br = null;
                    boolean comment = false;
                    br = new BufferedReader(new FileReader(file));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        allLines++;
                        line = line.trim();
                        if (line.matches("^[//s&&[^//n]]*$")) {
                            emptyLines++;
                        } else if (line.startsWith(doubleNotes[0]) && !line.endsWith(doubleNotes[1])) {
                            commentLines++;
                            comment = true;

                        } else if (true == comment) {
                            commentLines++;
                            if (line.endsWith(doubleNotes[1])) {
                                comment = false;
                            }
                        } else if (line.startsWith(singleNotes) || (line.startsWith(doubleNotes[0]) && line.endsWith(doubleNotes[1]))) {
                            commentLines++;
                        } else {
                            writtenLines++;
                        }
                    }
                    if (br != null) {
                        br.close();
                        br = null;
                    }
                }
            } else {
                treeFile(childs[i], type);
            }
        }
    }

    public static String[] doubleNoteType(String type) {
        String[] a = new String[2];
        switch (type) {
            case ".java":
                a[0] = "/*";
                a[1] = "*/";
                break;

            case ".py":
                a[0] = "\"\"\"";
                a[1] = a[0];
                break;
        }
        return a;
    }

    public static String singleNoteType(String type) {
        switch (type) {
            case ".java":
                return "//";

            case ".py":
                return "#";
        }
        return "";
    }
}