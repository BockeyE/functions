package UTILLS.Tools.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Java语言相关工具
 * 
 * @author PanJun
 * 
 */
@SuppressWarnings("all")
public final class LANG {

    /** Web应用上下文，由框架启动时注入 */
    public static String webCtx;

    static class FilterPrintStream extends PrintStream {

        public final List<String> messages = new ArrayList<String>();

        private static final String[] INNER_MASKS = { " sun.", " java.", " javax.", " $Proxy" };

        private final String[] maskTexts;

        private static final OutputStream OUT_STREAM = new ByteArrayOutputStream();

        public FilterPrintStream(String[] maskTexts) {
            super(OUT_STREAM);
            this.maskTexts = maskTexts;
        }

        @Override
        public void println(Object o) {
            if (o == null) {
                return;
            }

            String s = o + "";
            if (s.startsWith("\t...")) {
                return;
            } else if (s.startsWith("\tat ")) {
                if (contains(s, INNER_MASKS) || contains(s, maskTexts)) {
                    return;
                }
            }

            messages.add(s);
        }

        private boolean contains(String s, String[] masks) {
            for (int i = masks.length - 1; i > -1; i--) {
                String word = masks[i];
                if (STR.containsText(s, word)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 获取异常堆栈文本列表,可根据屏蔽包含关键字的行
     * 
     * @param e
     * @param maskTexts
     * @return
     */
    public static List<String> stackMessages(Throwable e, String... maskTexts) {
        FilterPrintStream ps = new FilterPrintStream(maskTexts);
        e.printStackTrace(ps);
        return ps.messages;
    }

    /**
     * 获取异常堆栈文本列表,可根据屏蔽包含关键字的行
     * 
     * @param e
     * @param maskTexts
     */
    public static StringBuilder stackMessage(StringBuilder sbMsg, Throwable e, String... maskTexts) {
        for (String l : stackMessages(e, maskTexts)) {
            sbMsg.append("\n").append(l);
        }
        return sbMsg;
    }

    /**
     * Add all fields of clazz to set object, contains private/protected/package
     * visibility fields
     * 
     * @param clazz
     * @return
     */
    public static Set<Field> findAllFields(Class<?> clazz) {
        Set<Field> ret = new HashSet<Field>();
        if (clazz != null) {
            while (clazz != null) {
                ret.addAll(Arrays.asList(clazz.getDeclaredFields()));
                clazz = clazz.getSuperclass();
            }
        }
        return ret;
    }

    /**
     * Add all fields of clazz to set object, which marked "annClazz"
     * 
     * @param clazz
     * @param annClazz
     * @return
     */
    public static List<Field> findAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annClazz) {
        List<Field> ret = new ArrayList<Field>();
        for (Field f : LANG.findAllFields(clazz)) {
            for (Annotation a : f.getAnnotations()) {
                if (annClazz.isAssignableFrom(a.getClass())) {
                    ret.add(f);
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * get annotation which marked "annClazz"
     * 
     * @param clazz
     * @param annClazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annClazz) {
        for (Annotation ann : clazz.getAnnotations()) {
            if (annClazz.isAssignableFrom(ann.getClass())) {
                return (T) ann;
            }
        }
        return null;
    }

    /**
     * get field which marked "name"
     * 
     * @param clazz
     * @param name
     * @return
     */
    public static Field getFieldByName(Class<?> clazz, String name) {
        for (Field f : LANG.findAllFields(clazz)) {
            if (f.getName().equals(name)) {
                return f;
            }

        }
        return null;
    }

    public static <T> T findExceptionInStack(Throwable e, Class<T> clazz) {
        while (e != null && !(clazz.isInstance(e))) {
            e = e.getCause();
        }
        return (T) e;
    }

    /**
     * 把一个给定的应用内的url加上上下文
     * 
     * @param relativeUrl
     * @return
     */
    public static String fixUrl(String relativeUrl) {
        if (relativeUrl == null) {
            return null;
        }

        while (relativeUrl.startsWith("/")) {
            relativeUrl = relativeUrl.substring(1);
        }

        if (webCtx.length() == 0) {
            return "/" + relativeUrl;
        } else {
            return webCtx + "/" + relativeUrl;
        }
    }

    /**
     * 取得类对象在文件系统中的根目录；如果在jar中，获取jar所在的目录
     * 
     * @param clazz
     * @return
     */
    public static String toClassRoot(Class<?> clazz) {
        StringBuilder name = new StringBuilder(clazz.getName());
        for (int i = name.length() - 1; i > -1; i--) {
            if (name.charAt(i) == '.') {
                name.setCharAt(i, '/');
            }
        }
        name.append(".class");
        URL url = clazz.getClassLoader().getResource(name.toString());
        String path = url.getPath();
        String className = clazz.getName();
        int pkgLevel = 0;
        for (int i; (i = className.indexOf(".")) > -1;) {
            pkgLevel++;
            className = className.substring(i + 1);
        }
        if ("jar".equals(url.getProtocol())) {
            path = path.substring("file:/".length() - 1);
            pkgLevel++;
        }
        for (int i = pkgLevel + 1; i > 0; i--) {
            int j = path.lastIndexOf("/");
            path = path.substring(0, j);
        }

        return new File(path).getAbsolutePath();
    }

}