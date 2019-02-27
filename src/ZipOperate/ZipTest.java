package ZipOperate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author bockey
 */
public class ZipTest {

    public static void main(String[] args) throws IOException {

        ZipFile zf = new ZipFile("C:\\Users\\user1\\Desktop\\auto.zip");

        ZipFile aim = new ZipFile("C:\\Users\\user1\\Desktop\\front\\auto222.zip");


    }

    public static void compress(String srcf, String destf) throws IOException {
        File src = new File(srcf);
        File dest = new File(destf);

        FileOutputStream fops=new FileOutputStream(dest);
        ZipEntry ze = new ZipEntry(srcf);

    }

}
