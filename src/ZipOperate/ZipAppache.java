package ZipOperate;


import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipInputStream;

/**
 * @author bockey
 */
public class ZipAppache {
    public static void main(String[] args) throws IOException {
        String zf = ("C:\\Users\\user1\\Desktop\\bh.jpg");
        String aim = ("C:\\Users\\user1\\Desktop\\front\\auto222.zip");
//        compressFile(zf, aim);
        String dezip = "C:\\Users\\user1\\Desktop\\auto\\a.jpg";
//        decompressFile(aim, dezip);

        String au = "C:\\Users\\user1\\Desktop\\auto.zip";
        decomDirTest(au);
    }

    public static void decomDir(String srcf) throws IOException {
        File srf = new File(srcf);
        ZipFile zf = new ZipFile(srf);
        Enumeration<ZipEntry> entries = zf.getEntries();
        ZipEntry entry = null;
        System.out.println();
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            System.out.println(entry.getName());
            System.out.println(entry.isDirectory());
            System.out.println(entry);
        }
    }



    public static void decomDirTest(String srcf) throws IOException {
        File srf = new File(srcf);
        FileInputStream fis = new FileInputStream(srf);
        ZipInputStream zis = new ZipInputStream(fis);
//        ZipFile zz = new ZipFile();
        int available = zis.available();
        System.out.println(available);

        int i = 0;
        while (i < 18) {
            ZipEntry z = new ZipEntry(zis.getNextEntry());
            System.out.println(z);
//            entry = entries.nextElement();
//            System.out.println(entry.getName());
//            System.out.println(entry.isDirectory());
            System.out.println(zis.getNextEntry());
            i++;
        }
    }

    /**
     *  in springboot, creat a tem file ,and save multipart content, then use zipfile
     */
//    public void ZipReadAndSave(MultipartFile aim) throws IOException {
//        String name = aim.getName();
//        File tem = File.createTempFile(UUID.randomUUID().toString(), "zip");
//        aim.transferTo(tem);
//    }
}
