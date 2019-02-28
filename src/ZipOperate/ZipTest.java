package ZipOperate;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * @author bockey
 */
public class ZipTest {

    public static void main(String[] args) throws IOException {

        String zf = ("C:\\Users\\user1\\Desktop\\bh.jpg");

        String aim = ("C:\\Users\\user1\\Desktop\\front\\auto222.zip");

        compress(zf, aim);


        String dezip = "C:\\Users\\user1\\Desktop\\auto\\a.jpg";
        decompress(aim, dezip);
    }

    public static void compress(String srcf, String destf) throws IOException {
        File src = new File(srcf);
        File dest = new File(destf);

        FileOutputStream fops = new FileOutputStream(dest);
        CheckedOutputStream ccos = new CheckedOutputStream(fops, new CRC32());
//        这是两个过滤流，用于维护数据校验和。校验和是用于维护数据完整性的一项技术。
//        先来简单聊聊校验和，校验和(checksum)，是冗余校验的一种形式。
//        它是通过错误检测方法，对经过空间（如通信）或者时间（如计算机存储）传送的数据的完整性进行检查的一种简单方法。
//        循环冗余校验CRC

        ZipOutputStream zos = new ZipOutputStream(ccos);


        //zipentry 是表示在压缩文件内的条目名称，带路径的话压缩包内也会是分层的
        //文件输入由ips读到byte[]中决定，不需要在这里指定
        ZipEntry ze = new ZipEntry("bh.jpg");

        zos.putNextEntry(ze);

        FileInputStream fip = new FileInputStream(src);

        byte[] tem = new byte[1024];
        int read = 0;

        while ((read = fip.read(tem)) != -1) {
            zos.write(tem, 0, read);

        }

        zos.close();
        fip.close();
        fops.close();
        ccos.close();

    }

    public static void decompress(String srcf, String destf) throws IOException {
        File srf = new File(srcf);
        ZipFile zf = new ZipFile(srf);
        Enumeration<? extends ZipEntry> entries = zf.entries();
        ZipEntry entry = null;

//        while (entries.hasMoreElements()) {
        entry = entries.nextElement();
        String path = destf + File.separator + entry.getName();
        File aim = new File(path);
        InputStream inputStream = zf.getInputStream(entry);
        FileOutputStream fop = new FileOutputStream(destf);

        byte[] tem = new byte[1024];
        int read = 0;
        while ((read = inputStream.read(tem)) != -1) {
            fop.write(tem, 0, read);
        }
        inputStream.close();
        fop.close();


//        }

    }




}
