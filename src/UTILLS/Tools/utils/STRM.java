package UTILLS.Tools.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO Stream 工具
 * 
 * @author PanJUn
 * 
 */
public final class STRM {

    /**
     * 内存流，可以先向这个流中写入数据，然后调用asInputStream能把数据转换成输入流；<br>
     * 输入流和输出流内存数据是共享的，所以比直接使用JDK API节省内存
     * 
     * @author PanJun
     * 
     */
    public static class MemStream extends ByteArrayOutputStream {

        public ByteArrayInputStream asInputStream() {
            return new ByteArrayInputStream(buf, 0, count);
        }

        public byte[] getBuffer() {
            return buf;
        }

    }

    public static void closeIO(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                // Null
            }
            closeable = null;
        }
    }

    /**
     * 把InputStream内容输出到OutputStream，完成后InputStream， OutputStream都会被关闭
     * 
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copy(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buffer = new byte[64 * 1024];
            for (int len; (len = in.read(buffer)) > 0;) {
                out.write(buffer, 0, len);
            }
        } finally {
            closeIO(in);
            closeIO(out);
        }
    }

}