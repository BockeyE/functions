package UTILLS.Tools.utils;

import UTILLS.Tools.FunctionX;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;



/**
 * 文件相关的操作类
 * 
 * @author PanJun
 * 
 */
public final class FILE {

    /**
     * 从路径中提取文件名 D:\1\a.txt ==> a.txt
     * 
     * @param fname
     * @return
     */
    public static String name(String fname) {
        if (fname == null)
            return null;

        for (int i = fname.length() - 1; i > -1; i--) {
            char c = fname.charAt(i);
            if (c == '/' || c == '\\')
                return fname.substring(i + 1);
        }

        return fname;
    }

    /**
     * 创建文件夹
     * 
     * @param folder
     */
    public static void mkdirs(File folder) {
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * 改变文件名的后缀
     * 
     * @param fileName
     * @param ext
     * @return
     */
    public static String ext(String fileName, String ext) {
        if (fileName == null)
            return null;

        int i = fileName.lastIndexOf(".");
        String result = fileName;
        if (i > -1) {
            result = fileName.substring(0, i);
        }

        if (ext != null) {
            ext = ext.trim();
            if (ext.startsWith("."))
                ext = ext.substring(1);
        } else {
            ext = "";
        }

        if ("".equals(ext))
            return result;
        else
            return result + "." + ext;
    }

    /**
     * 从路径中提取文件后缀名（带点）:<br>
     * D:\1\a.txt ==> .txt<br>
     * D:\1\a_txt ==> 空串
     * 
     * @param fname
     * @return
     */
    public static String ext(String fname) {
        if (fname == null)
            return null;

        int i = fname.lastIndexOf(".");
        if (i == -1)
            return "";
        return fname.substring(i);
    }

    public static String extLower(String fname) {
        return STR.lower(ext(fname));
    }

    public static String extUpper(String fname) {
        return STR.upper(ext(fname));
    }

    /**
     * 从路径中提取文件后缀名（不带点）:<br>
     * D:\1\a.txt ==> .txt<br>
     * D:\1\a_txt ==> 空串
     * 
     * @param fname
     * @return
     */
    public static String extNoDot(String fname) {
        if (fname == null)
            return null;

        int i = fname.lastIndexOf(".");
        if (i == -1)
            return "";
        return fname.substring(i + 1);
    }

    /**
     * 整理文件名，把所有的"\"转换成"/",
     * 
     * @param fName
     * @return
     */
    public static String unifySlash(String fName) {
        if (fName == null)
            return null;

        StringBuilder ret = new StringBuilder(fName);
        for (int i = 0, len = ret.length(); i < len; i++) {
            char c = fName.charAt(i);
            if (c == '\\')
                ret.setCharAt(i, '/');
        }

        return ret.toString();
    }

    public static boolean delete(String fileName) {
        return delete(new File(fileName));
    }

    public static boolean delete(File file) {
        if (!file.exists())
            return true;

        if (file.isDirectory()) {
            for (File sub : file.listFiles()) {
                delete(sub);
            }
        }
        return file.delete();
    }

    public static boolean copy(String source, String target) {
        return doCopyFile(new File(source), new File(target), new byte[1024 * 1024]);
    }

    public static boolean copy(File source, File target) {
        return doCopyFile(source, target, new byte[1024 * 1024]);
    }

    public static boolean copyToDir(String file, String directory) {
        File srcFile = new File(file);
        if (!srcFile.exists())
            return false;

        File dirFile = new File(directory);
        return doCopyFile(srcFile, new File(dirFile, srcFile.getName()), new byte[1024 * 1024]);
    }

    protected static boolean doCopyFile(File srcFile, File destFile, byte[] buffer) {
        if (!srcFile.exists())
            return false;

        if (!srcFile.isDirectory()) {
            try {
                destFile.getParentFile().mkdirs();

                FileInputStream src = new FileInputStream(srcFile);
                FileOutputStream dest = new FileOutputStream(destFile);
                try {
                    for (int size; (size = src.read(buffer)) > 0;) {
                        dest.write(buffer, 0, size);
                    }
                } finally {
                    closeIO(src);
                    closeIO(dest);
                }
            } catch (Exception e) {
                return false;
            }
        } else {
            if (destFile.exists() && destFile.isFile()) {
                if (!destFile.delete())
                    return false;
            }

            destFile.mkdirs();
            for (File srcSubFile : srcFile.listFiles()) {
                File destSubFile = new File(destFile, srcSubFile.getName());
                if (!doCopyFile(srcSubFile, destSubFile, buffer))
                    return false;
            }
        }

        return true;
    }

    public static void closeIO(Closeable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        } catch (IOException e) {
            // NULL;
        }
    }

    /**
     * 包含文件单位的大小
     * 
     * @param size
     * @return
     */
    public static String sizeOfUnit(long size) {
        if (size <= 0) {
            return "0B";
        }

        BigDecimal bdSize = null;
        String unit = "";
        if (size < 1024) {
            bdSize = new BigDecimal(size);
            unit = "B";
        } else if (size < 1024 * 1024) {
            bdSize = new BigDecimal(size);
            bdSize = bdSize.divide(new BigDecimal(1024));
            unit = "K";
        } else if (size < 1024 * 1024 * 1024) {
            bdSize = new BigDecimal(size);
            bdSize = bdSize.divide(new BigDecimal(1024 * 1024));
            unit = "M";
        } else {
            bdSize = new BigDecimal(size);
            bdSize = bdSize.divide(new BigDecimal(1024 * 1024 * 1024));
            unit = "G";
        }
        bdSize = bdSize.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bdSize + unit;
    }

    /**
     * 从包含单位的大小提取文件大小
     * 
     * @param unitedSize
     * @return
     */
    public static Long parseSize(String unitedSize) {
        if (STR.isBlank(unitedSize)) {
            return null;
        }

        final String usize = unitedSize.trim().toUpperCase();
        StringBuilder sb = new StringBuilder(usize);
        AtomicLong factor = new AtomicLong(1);
        BiConsumer<String, Long> fnEnd = (suffix, newFactor) -> {
            if (usize.endsWith(suffix)) {
                sb.delete(unitedSize.length() - suffix.length(), unitedSize.length());
                factor.set(newFactor);
            }
        };
        fnEnd.accept("K", 1024L);
        fnEnd.accept("KB", 1024L);
        fnEnd.accept("M", 1024l * 1024);
        fnEnd.accept("MB", 1024l * 1024);
        fnEnd.accept("G", 1024l * 1024 * 1024);
        fnEnd.accept("GB", 1024l * 1024 * 1024);
        String s = sb.toString().replace(",", "");
        Long ret = STR.toLong(s);
        if (ret != null) {
            ret = ret * factor.get();
        }
        return ret;
    }
//
//    private static MimetypesFileTypeMap MIME_TYPE_MAP = new MimetypesFileTypeMap();
//    static {
//        MIME_TYPE_MAP.addMimeTypes("application/x-bmp bmp");
//        MIME_TYPE_MAP.addMimeTypes("audio/mp3 mp3");
//        MIME_TYPE_MAP.addMimeTypes("application/andrew-inset ez");
//        MIME_TYPE_MAP.addMimeTypes("application/mac-binhex40 hqx");
//        MIME_TYPE_MAP.addMimeTypes("application/mac-compactpro cpt");
//        MIME_TYPE_MAP.addMimeTypes("application/msword doc");
//        MIME_TYPE_MAP.addMimeTypes("application/octet-stream bin");
//        MIME_TYPE_MAP.addMimeTypes("application/octet-stream dms");
//        MIME_TYPE_MAP.addMimeTypes("application/octet-stream lha");
//        MIME_TYPE_MAP.addMimeTypes("application/octet-stream lzh");
//        MIME_TYPE_MAP.addMimeTypes("application/octet-stream exe");
//        MIME_TYPE_MAP.addMimeTypes("application/octet-stream class");
//        MIME_TYPE_MAP.addMimeTypes("application/octet-stream so");
//        MIME_TYPE_MAP.addMimeTypes("application/octet-stream dll");
//        MIME_TYPE_MAP.addMimeTypes("application/oda oda");
//        MIME_TYPE_MAP.addMimeTypes("application/pdf pdf");
//        MIME_TYPE_MAP.addMimeTypes("application/postscript ai");
//        MIME_TYPE_MAP.addMimeTypes("application/postscript eps");
//        MIME_TYPE_MAP.addMimeTypes("application/postscript ps");
//        MIME_TYPE_MAP.addMimeTypes("application/smil smi");
//        MIME_TYPE_MAP.addMimeTypes("application/smil smil");
//        MIME_TYPE_MAP.addMimeTypes("application/vnd.mif mif");
//        MIME_TYPE_MAP.addMimeTypes("application/vnd.ms-excel xls");
//        MIME_TYPE_MAP.addMimeTypes("application/vnd.ms-powerpoint ppt");
//        MIME_TYPE_MAP.addMimeTypes("application/vnd.wap.wbxml wbxml");
//        MIME_TYPE_MAP.addMimeTypes("application/vnd.wap.wmlc wmlc");
//        MIME_TYPE_MAP.addMimeTypes("application/vnd.wap.wmlscriptc wmlsc");
//        MIME_TYPE_MAP.addMimeTypes("application/x-bcpio bcpio");
//        MIME_TYPE_MAP.addMimeTypes("application/x-cdlink vcd");
//        MIME_TYPE_MAP.addMimeTypes("application/x-chess-pgn pgn");
//        MIME_TYPE_MAP.addMimeTypes("application/x-cpio cpio");
//        MIME_TYPE_MAP.addMimeTypes("application/x-csh csh");
//        MIME_TYPE_MAP.addMimeTypes("application/x-director dcr");
//        MIME_TYPE_MAP.addMimeTypes("application/x-director dir");
//        MIME_TYPE_MAP.addMimeTypes("application/x-director dxr");
//        MIME_TYPE_MAP.addMimeTypes("application/x-dvi dvi");
//        MIME_TYPE_MAP.addMimeTypes("application/x-futuresplash spl");
//        MIME_TYPE_MAP.addMimeTypes("application/x-gtar gtar");
//        MIME_TYPE_MAP.addMimeTypes("application/x-hdf hdf");
//        MIME_TYPE_MAP.addMimeTypes("application/x-javascript js");
//        MIME_TYPE_MAP.addMimeTypes("application/x-koan skp");
//        MIME_TYPE_MAP.addMimeTypes("application/x-koan skd");
//        MIME_TYPE_MAP.addMimeTypes("application/x-koan skt");
//        MIME_TYPE_MAP.addMimeTypes("application/x-koan skm");
//        MIME_TYPE_MAP.addMimeTypes("application/x-latex latex");
//        MIME_TYPE_MAP.addMimeTypes("application/x-netcdf nc");
//        MIME_TYPE_MAP.addMimeTypes("application/x-netcdf cdf");
//        MIME_TYPE_MAP.addMimeTypes("application/x-sh sh");
//        MIME_TYPE_MAP.addMimeTypes("application/x-shar shar");
//        MIME_TYPE_MAP.addMimeTypes("application/x-shockwave-flash swf");
//        MIME_TYPE_MAP.addMimeTypes("application/x-stuffit sit");
//        MIME_TYPE_MAP.addMimeTypes("application/x-sv4cpio sv4cpio");
//        MIME_TYPE_MAP.addMimeTypes("application/x-sv4crc sv4crc");
//        MIME_TYPE_MAP.addMimeTypes("application/x-tar tar");
//        MIME_TYPE_MAP.addMimeTypes("application/x-tcl tcl");
//        MIME_TYPE_MAP.addMimeTypes("application/x-tex tex");
//        MIME_TYPE_MAP.addMimeTypes("application/x-texinfo texinfo");
//        MIME_TYPE_MAP.addMimeTypes("application/x-texinfo texi");
//        MIME_TYPE_MAP.addMimeTypes("application/x-troff t");
//        MIME_TYPE_MAP.addMimeTypes("application/x-troff tr");
//        MIME_TYPE_MAP.addMimeTypes("application/x-troff roff");
//        MIME_TYPE_MAP.addMimeTypes("application/x-troff-man man");
//        MIME_TYPE_MAP.addMimeTypes("application/x-troff-me me");
//        MIME_TYPE_MAP.addMimeTypes("application/x-troff-ms ms");
//        MIME_TYPE_MAP.addMimeTypes("application/x-ustar ustar");
//        MIME_TYPE_MAP.addMimeTypes("application/x-wais-source src");
//        MIME_TYPE_MAP.addMimeTypes("application/xhtml+xml xhtml");
//        MIME_TYPE_MAP.addMimeTypes("application/xhtml+xml xht");
//        MIME_TYPE_MAP.addMimeTypes("application/zip zip");
//        MIME_TYPE_MAP.addMimeTypes("audio/basic au");
//        MIME_TYPE_MAP.addMimeTypes("audio/basic snd");
//        MIME_TYPE_MAP.addMimeTypes("audio/midi mid");
//        MIME_TYPE_MAP.addMimeTypes("audio/midi midi");
//        MIME_TYPE_MAP.addMimeTypes("audio/midi kar");
//        MIME_TYPE_MAP.addMimeTypes("audio/mpeg mpga");
//        MIME_TYPE_MAP.addMimeTypes("audio/mpeg mp2");
//        MIME_TYPE_MAP.addMimeTypes("audio/mpeg mp3");
//        MIME_TYPE_MAP.addMimeTypes("audio/x-aiff aif");
//        MIME_TYPE_MAP.addMimeTypes("audio/x-aiff aiff");
//        MIME_TYPE_MAP.addMimeTypes("audio/x-aiff aifc");
//        MIME_TYPE_MAP.addMimeTypes("audio/x-mpegurl m3u");
//        MIME_TYPE_MAP.addMimeTypes("audio/x-pn-realaudio ram");
//        MIME_TYPE_MAP.addMimeTypes("audio/x-pn-realaudio rm");
//        MIME_TYPE_MAP.addMimeTypes("audio/x-pn-realaudio-plugin rpm");
//        MIME_TYPE_MAP.addMimeTypes("audio/x-realaudio ra");
//        MIME_TYPE_MAP.addMimeTypes("audio/x-wav wav");
//        MIME_TYPE_MAP.addMimeTypes("chemical/x-pdb pdb");
//        MIME_TYPE_MAP.addMimeTypes("chemical/x-xyz xyz");
//        MIME_TYPE_MAP.addMimeTypes("image/bmp bmp");
//        MIME_TYPE_MAP.addMimeTypes("image/gif gif");
//        MIME_TYPE_MAP.addMimeTypes("image/ief ief");
//        MIME_TYPE_MAP.addMimeTypes("image/jpeg jpeg");
//        MIME_TYPE_MAP.addMimeTypes("image/jpeg jpg");
//        MIME_TYPE_MAP.addMimeTypes("image/jpeg jpe");
//        MIME_TYPE_MAP.addMimeTypes("image/png png");
//        MIME_TYPE_MAP.addMimeTypes("image/tiff tiff");
//        MIME_TYPE_MAP.addMimeTypes("image/tiff tif");
//        MIME_TYPE_MAP.addMimeTypes("image/vnd.djvu djvu");
//        MIME_TYPE_MAP.addMimeTypes("image/vnd.djvu djv");
//        MIME_TYPE_MAP.addMimeTypes("image/vnd.wap.wbmp wbmp");
//        MIME_TYPE_MAP.addMimeTypes("image/x-cmu-raster ras");
//        MIME_TYPE_MAP.addMimeTypes("image/x-portable-anymap pnm");
//        MIME_TYPE_MAP.addMimeTypes("image/x-portable-bitmap pbm");
//        MIME_TYPE_MAP.addMimeTypes("image/x-portable-graymap pgm");
//        MIME_TYPE_MAP.addMimeTypes("image/x-portable-pixmap ppm");
//        MIME_TYPE_MAP.addMimeTypes("image/x-rgb rgb");
//        MIME_TYPE_MAP.addMimeTypes("image/x-xbitmap xbm");
//        MIME_TYPE_MAP.addMimeTypes("image/x-xpixmap xpm");
//        MIME_TYPE_MAP.addMimeTypes("image/x-xwindowdump xwd");
//        MIME_TYPE_MAP.addMimeTypes("model/iges igs");
//        MIME_TYPE_MAP.addMimeTypes("model/iges iges");
//        MIME_TYPE_MAP.addMimeTypes("model/mesh msh");
//        MIME_TYPE_MAP.addMimeTypes("model/mesh mesh");
//        MIME_TYPE_MAP.addMimeTypes("model/mesh silo");
//        MIME_TYPE_MAP.addMimeTypes("model/vrml wrl");
//        MIME_TYPE_MAP.addMimeTypes("model/vrml vrml");
//        MIME_TYPE_MAP.addMimeTypes("text/css css");
//        MIME_TYPE_MAP.addMimeTypes("text/html html");
//        MIME_TYPE_MAP.addMimeTypes("text/html htm");
//        MIME_TYPE_MAP.addMimeTypes("text/plain asc");
//        MIME_TYPE_MAP.addMimeTypes("text/plain txt");
//        MIME_TYPE_MAP.addMimeTypes("text/plain java");
//        MIME_TYPE_MAP.addMimeTypes("text/richtext rtx");
//        MIME_TYPE_MAP.addMimeTypes("text/rtf rtf");
//        MIME_TYPE_MAP.addMimeTypes("text/sgml sgml");
//        MIME_TYPE_MAP.addMimeTypes("text/sgml sgm");
//        MIME_TYPE_MAP.addMimeTypes("text/tab-separated-values tsv");
//        MIME_TYPE_MAP.addMimeTypes("text/vnd.wap.wml wml");
//        MIME_TYPE_MAP.addMimeTypes("text/vnd.wap.wmlscript wmls");
//        MIME_TYPE_MAP.addMimeTypes("text/x-setext etx");
//        MIME_TYPE_MAP.addMimeTypes("text/xml xsl");
//        MIME_TYPE_MAP.addMimeTypes("text/xml xml");
//        MIME_TYPE_MAP.addMimeTypes("video/mpeg mpeg");
//        MIME_TYPE_MAP.addMimeTypes("video/mpeg mpg");
//        MIME_TYPE_MAP.addMimeTypes("video/mpeg mpe");
//        MIME_TYPE_MAP.addMimeTypes("video/quicktime qt");
//        MIME_TYPE_MAP.addMimeTypes("video/quicktime mov");
//        MIME_TYPE_MAP.addMimeTypes("video/vnd.mpegurl mxu");
//        MIME_TYPE_MAP.addMimeTypes("video/x-msvideo avi");
//        MIME_TYPE_MAP.addMimeTypes("video/x-sgi-movie movie");
//        MIME_TYPE_MAP.addMimeTypes("x-conference/x-cooltalk ice");
//        MIME_TYPE_MAP.addMimeTypes("application/x-www-form-urlencoded form");
//        MIME_TYPE_MAP.addMimeTypes("video/mp4 mp4");
//        MIME_TYPE_MAP.addMimeTypes("video/x-flv flv");
//        MIME_TYPE_MAP.addMimeTypes("text/html html");
//        MIME_TYPE_MAP.addMimeTypes("text/html htm");
//    }
//
//    public static String contentType(File file) {
//        return MIME_TYPE_MAP.getContentType(file);
//    }
//
//    public static String contentType(String fileName) {
//        return MIME_TYPE_MAP.getContentType(fileName);
//    }

    /**
     * 解压文件到指定目录 解压后的文件名，和之前一致
     * 
     * @param zipPath
     *            待解压的zip文件路径
     * @param descDir
     *            指定目录
     */
    public static void unzip(String zipPath, String descDir) throws IOException {
        ZipFile zip = null;
        try {
            zip = new ZipFile(new File(zipPath), Charset.forName("GBK"));// 解决中文文件夹乱码
            String name = zip.getName().substring(zip.getName().lastIndexOf('\\') + 1, zip.getName().lastIndexOf('.'));

            File pathFile = new File(descDir + name);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }

            for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                String outPath = (descDir + name + "/" + zipEntryName).replaceAll("\\*", "/");

                // 判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if (!file.exists()) {
                    file.mkdirs();
                }
                // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }

                FileOutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
        } finally {
            zip.close();
        }
        return;
    }

    /**
     * 解压压缩流，业务系统负责实现fnOutput方法，如果fnOutput返回OutputStream为空时，将忽略该ZipEntry
     * 
     * @param instrm
     * @param fnOutput
     * @throws IOException
     */
    public static void unzip(InputStream instrm, FunctionX<String, OutputStream> fnOutput) throws IOException {
        if (instrm == null || fnOutput == null) {
            return;
        }

        try (ZipInputStream zis = new ZipInputStream(instrm, Charset.forName("GBK"))) {
            byte[] buf = new byte[1024 * 64];
            for (ZipEntry e = zis.getNextEntry(); e != null; e = zis.getNextEntry()) {
                String name = e.getName();
                try {
                    OutputStream outStrm = fnOutput.apply(name);
                    if (outStrm != null) {
                        try {
                            for (int read; (read = zis.read(buf, 0, buf.length)) > 0;) {
                                outStrm.write(buf, 0, read);
                            }
                        } finally {
                            outStrm.close();
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

}