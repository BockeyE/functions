package AAAAAAPracs.ZipOperate;

import com.supervisionProj.supervision.domian.FileEntity;
import org.apache.tools.zip.ZipEntry;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author bockey
 */
public class ZipUtils {

    public static void compressFile(OutputStream ops, List<FileEntity> files) throws IOException {
        CheckedOutputStream ccos = new CheckedOutputStream(ops, new CRC32());
//        这是两个过滤流，用于维护数据校验和。校验和是用于维护数据完整性的一项技术。
//        先来简单聊聊校验和，校验和(checksum)，是冗余校验的一种形式。
//        它是通过错误检测方法，对经过空间（如通信）或者时间（如计算机存储）传送的数据的完整性进行检查的一种简单方法。
//        循环冗余校验CRC
        ZipOutputStream zos = new ZipOutputStream(ccos);
        //zipentry 是表示在压缩文件内的条目名称，带路径的话压缩包内也会是分层的
        //文件输入由ips读到byte[]中决定，不需要在这里指定

        int m = 1;
        for (FileEntity file : files) {
            ZipEntry ze = new ZipEntry("" + m + "-" + file.getFileName());
            zos.putNextEntry(ze);
            zos.write(file.getFileBytes());
            zos.flush();
            zos.closeEntry();
            m++;
        }
        zos.flush();
        zos.finish();
        zos.close();
        closeAll(zos, ccos);

    }


    private static void closeAll(Closeable... c) {
        for (Closeable closeable : c) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FileEntity f1 = new FileEntity();
        f1.setFileName("name1.txt");
        byte[] buf = {56, 57, 58, 59, 99, 98};
        f1.setFileBytes(buf);
        FileEntity f2 = new FileEntity();
        f2.setFileName("name2.txt");
        f2.setFileBytes(buf);
        List l = new ArrayList();
        l.add(f1);
        l.add(f2);
        FileOutputStream fos = new FileOutputStream("C:\\ZZBK\\axa.zip");
        compressFile(fos, l);
    }

}
