package UTILLS.Tools.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类，方法签名一定要直白、简洁，尽量作到不需要注释
 * 
 * @author PanJun
 * 
 */
public final class STR {

    public static String delHtmlTag(String htmlStr) {
        if (htmlStr != null && htmlStr != "") {
            try {
                htmlStr = htmlStr.replace("&quot;", "“");
                htmlStr = htmlStr.replace("&amp;", "&");
                htmlStr = htmlStr.replace("&lt;", "<");
                htmlStr = htmlStr.replace("&gt;", ">");
                htmlStr = htmlStr.replace("&nbsp;", " ");
            } catch (Exception e) {

            }
            String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
            String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

        }
        return htmlStr.trim(); // 返回文本字符串
    }

    public static String delHeader(String s, String header) {
        if (s == null || header == null)
            return null;
        if (s.startsWith(header)) {
            return s.substring(header.length());
        }
        return s;
    }

    static Map<Character, String> htmlCharsMap = initHtmlCharsMap();

    /**
     * 处理一个字符串为了在页面上展示
     * 
     * @param s
     * @return
     */
    public static String handleHtmlForUi(String s, char... chars) {
        StringBuilder sb = new StringBuilder();
        s = NULL.nvl(s);
        Set<Character> inChars = new HashSet<>();
        for (char c : chars) {
            inChars.add(c);
        }

        int htmlTag = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            char c = s.charAt(i);
            if (c == '<') {
                htmlTag++;
            } else if (c == '>') {
                htmlTag--;
            }

            if (inChars.contains(c)) {
                String mapped = htmlCharsMap.get(c);
                if (c == ' ' && htmlTag == 0) {
                    int blanks = 0;
                    for (int k = i; k < len && s.charAt(k) == ' '; k++) {
                        blanks++;
                        i = k;
                    }
                    sb.append("<span style='margin-left:");
                    sb.append(blanks * 8);
                    sb.append("px'></span>");
                } else if (mapped != null) {
                    sb.append(mapped);
                } else {
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static Map<Character, String> initHtmlCharsMap() {
        Map<Character, String> ret = new HashMap<Character, String>();
        ret.put('>', "&gt;");
        ret.put('<', "&lt;");
        ret.put('&', "&amp;");
        ret.put('\n', "<br>");
        return ret;
    }

    public static String toString(Object o) {
        return o == null ? null : o.toString();
    }

    public static boolean isNumeric(String str) {
        if (!isBlank(str) && str.matches("\\d*")) {
            return true;
        } else {
            return false;
        }
    }

    public static Long toLong(Object s) {
        if (s == null) {
            return null;
        } else {
            String str = s.toString().trim();
            if (str.length() == 0) {
                return null;
            }

            try {
                return Long.parseLong(str);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public static Integer toInteger(Object s) {
        if (s == null) {
            return null;
        } else {
            String str = s.toString().trim();
            if (str.length() == 0) {
                return null;
            }

            try {
                return Integer.parseInt(str);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public static Double toDouble(Object s) {
        if (s == null) {
            return null;
        } else {
            String str = s.toString().trim();
            if (str.length() == 0) {
                return null;
            }

            try {
                return Double.parseDouble(str);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public static BigDecimal toBigDecimal(Object s) {
        if (s == null) {
            return null;
        } else {
            if (s instanceof BigDecimal) {
                return (BigDecimal) s;
            }
            String str = s.toString().trim();
            if (str.length() == 0) {
                return null;
            }

            try {
                return new BigDecimal(str);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public static boolean notBlank(String s) {
        return !isBlank(s);
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().equals("");
    }

    public static String notBlank(String s, String blankReplacer) {
        if (isBlank(s)) {
            return blankReplacer;
        }
        return s;
    }

    public static String trim(String s) {
        return s == null ? null : s.trim();
    }

    public static void trim(StringBuilder sb) {
        if (sb == null) {
            return;
        }

        while (sb.length() > 0 && sb.charAt(0) <= ' ') {
            sb.delete(0, 1);
        }

        for (int len = sb.length(); len > 0 && sb.charAt(len - 1) <= ' '; len--) {
            sb.delete(0, 1);
        }
    }

    public static void trim(StringBuffer sb) {
        if (sb == null) {
            return;
        }

        while (sb.length() > 0 && sb.charAt(0) <= ' ') {
            sb.delete(0, 1);
        }

        for (int len = sb.length(); len > 0 && sb.charAt(len - 1) <= ' '; len--) {
            sb.delete(0, 1);
        }
    }

    public static String trimUpper(String s) {
        if (s == null) {
            return null;
        }
        return s.trim().toUpperCase();
    }

    public static String trimLower(String s) {
        if (s == null) {
            return null;
        }
        return s.trim().toLowerCase();
    }

    public static String upper(String s) {
        return s == null ? null : s.toUpperCase();
    }

    public static String lower(String s) {
        return s == null ? null : s.toLowerCase();
    }

    public static char upper(char c) {
        if (c < MAPPER_LEN) {
            return charMapper[c].upper;
        } else {
            return c;
        }
    }

    public static char lower(char c) {
        if (c < MAPPER_LEN) {
            return charMapper[c].lower;
        } else {
            return c;
        }
    }

    public static String nullToEmpty(String s) {
        if (s == null)
            return "";
        else
            return s;
    }

    public static boolean equals(String s1, String s2) {
        if (s1 != null)
            return s1.equals(s2);
        else if (s2 != null)
            return s2.equals(s1);
        else
            return true;
    }

    /** 限制字符长度，不会返回空 */
    public static String limit(String s, int maxLen) {
        if (s == null || s.length() == 0 || maxLen <= 0) {
            return "";
        }
        if (s.length() <= maxLen) {
            return s;
        }

        return s.substring(0, maxLen);
    }

    public static int compare(String s1, String s2) {
        if (s1 == null && s2 == null)
            return 0;
        else if (s1 == null)
            return -1;
        else if (s2 == null)
            return 1;
        else
            return s1.compareTo(s2);
    }

    /** 不区分大小写 */
    public static int compareText(String s1, String s2) {
        if (s1 == null && s2 == null)
            return 0;
        else if (s1 == null)
            return -1;
        else if (s2 == null)
            return 1;
        else
            return s1.compareToIgnoreCase(s2);
    }

    public static boolean equalsText(String s1, String s2) {
        if (s1 != null)
            return s1.equalsIgnoreCase(s2);
        else if (s2 != null)
            return s2.equalsIgnoreCase(s1);
        else
            return true;
    }

    public static boolean equalsAny(String s, Object... texts) {
        for (Object o : texts) {
            String text = o == null ? null : o.toString();
            if (equals(s, text))
                return true;
        }

        return false;
    }

    public static boolean equalsAny(String s, Collection<?> texts) {
        if (texts != null) {
            for (Object o : texts) {
                String text = o == null ? null : o.toString();
                if (equals(s, text))
                    return true;
            }
        }

        return false;
    }

    public static boolean equalsAnyText(String s, Object... texts) {
        for (Object o : texts) {
            String text = o == null ? null : o.toString();
            if (equalsText(s, text))
                return true;
        }

        return false;
    }

    public static boolean equalsAnyText(String s, Collection<?> texts) {
        if (texts != null) {
            for (Object o : texts) {
                String text = o == null ? null : o.toString();
                if (equalsText(s, text))
                    return true;
            }
        }

        return false;
    }

    public static int indexOfText(String s, Object... texts) {
        for (int i = 0; i < texts.length; i++) {
            Object o = texts[i];
            String text = o == null ? null : o.toString();
            if (equalsText(s, text))
                return i;
        }

        return -1;
    }

    public static int indexOfText(String s, List<?> texts) {
        for (int i = 0; i < texts.size(); i++) {
            Object o = texts.get(i);
            String text = o == null ? null : o.toString();
            if (equalsText(s, text))
                return i;
        }

        return -1;
    }

    public static String uuid() {
        String ret = UUID.randomUUID().toString();
        return ret.replaceAll("-", "");
    }

    /**
     * 拼接REST形式的url，eg: <br>
     * linkUrl("http://127.0.0.1/", "/1") -> http://127.0.0.1/1<br>
     * linkUrl("http://127.0.0.1", "1") -> http://127.0.0.1/1<br>
     * 
     * @param urls
     * @return
     */
    public static String linkUrl(Object... urls) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0, len = urls.length; i < len; i++) {
            String s = toString(urls[i]);
            if (s == null)
                s = "";

            if (i < len - 1) {
                while ((i != 0 || !"/".equals(s)) && s.endsWith("/")) {
                    s = s.substring(0, s.length() - 1);
                }
            }

            if (i > 0) {
                while (s.startsWith("/")) {
                    s = s.substring(1);
                }
            }

            if (ret.length() > 0 && s.length() > 0 && ret.charAt(ret.length() - 1) != '/')
                ret.append("/");
            ret.append(s);
        }
        return ret.toString();
    }

    /**
     * 转码a..z,A..Z,0..9集合以外的特殊字符
     * 
     * @param s
     * @return
     */
    public static String encodeChars(String s) {
        if (s == null) {
            return null;
        }

        // 处理windows到linux回车换行符替换符_d__a_
        s = s.replaceAll("\\\r", "");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
                ret.append(c);
            } else {
                ret.append("_").append(Integer.toHexString((int) c)).append("_");
            }
        }
        return ret.toString();
    }

    /**
     * encodeChars方法的恢复方法
     * 
     * @param s
     * @return
     */
    public static String decodeChars(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder arg = new StringBuilder((int) (s.length() * 1.3));
        int i = 0;
        int len = s.length();
        while (i < len) {
            char c = s.charAt(i);
            if (c != '_') {
                arg.append(c);
                i++;
            } else {
                int hi = -1;
                for (int k = i + 1; k < len; k++) {
                    if (s.charAt(k) == '_') {
                        hi = k;
                        break;
                    }
                }

                if (hi == -1 || i + 1 == hi) {
                    arg.append(c);
                    i++;
                } else {
                    String digital = s.substring(i + 1, hi);
                    try {
                        char reChar = (char) (Integer.parseInt(digital, 16));
                        arg.append(reChar);
                    } catch (Exception e) {
                        arg.append(c).append(digital).append(c);
                    }
                    i = hi + 1;
                }
            }
        }
        return arg.toString();
    }

    public static String toMd5(String s) {
        if (s == null) {
            return null;
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));

            StringBuilder ret = new StringBuilder();
            String stmp = "";
            for (int n = 0; n < bytes.length; n++) {
                stmp = (Integer.toHexString(bytes[n] & 0XFF).toUpperCase());
                if (stmp.length() == 1)
                    ret.append("0").append(stmp);
                else
                    ret.append(stmp);
            }
            return ret.toString();
        } catch (Exception ex) {
            throw new RuntimeException("toMd5 error arg s=[" + s + "]!", ex);
        }
    }

    /**
     * 转换密码的加密方法，先md5 再sha-256<br>
     * 如“1”加密后：<br>
     * C7540F8F70BA38FEA8F25F2426A2AC9619BF11A5
     * 
     * @param s
     * @return
     */
    public static String toPassword(String s) {
        if (s == null) {
            return null;
        }

        s = toMd5(s);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(s.getBytes("utf-8"));

            StringBuilder ret = new StringBuilder();
            String stmp = "";
            for (int n = 0; n < bytes.length; n++) {
                stmp = (Integer.toHexString(bytes[n] & 0XFF).toUpperCase());
                if (stmp.length() == 1)
                    ret.append("0").append(stmp);
                else
                    ret.append(stmp);
            }
            return ret.toString();
        } catch (Exception ex) {
            throw new RuntimeException("toMd5 error arg s=[" + s + "]!", ex);
        }
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     * 
     * @param str
     *            加密后的报文
     * @return
     */
    public static String toSha256(String str) {
        try {
            MessageDigest ret = MessageDigest.getInstance("SHA-256");
            ret.update(str.getBytes("UTF-8"));
            return byte2Hex(ret.digest());
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 将byte转为16进制
     * 
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder sbRet = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String s = Integer.toHexString(bytes[i] & 0xFF);
            if (s.length() == 1) {
                sbRet.append("0");
            }
            sbRet.append(s.toUpperCase());
        }
        return sbRet.toString();
    }

    /**
     * 不区分大小写的:String.indexOf
     * 
     * @param s
     * @param sub
     * @return
     */
    public static int indexOfText(CharSequence s, CharSequence sub) {
        int len = 0;
        int subLen = 0;
        if (s == null || sub == null || (len = s.length()) < (subLen = sub.length())) {
            return -1;
        }

        for (int i = 0, count = len - subLen + 1; i < count; i++) {
            boolean isEq = true;
            for (int k = 0, t = i; k < subLen; k++, t++) {
                if (lower(s.charAt(t)) != lower(sub.charAt(k))) {
                    isEq = false;
                    break;
                }
            }

            if (isEq) {
                return i;
            }
        }
        return -1;
    }

    public static boolean containsText(CharSequence s, CharSequence text) {
        return indexOfText(s, text) > -1;
    }

    private static void addToList(List<String> list, String s, boolean trim) {
        if (trim) {
            list.add(s.trim());
        } else {
            list.add(s);
        }
    }

    /**
     * 不区分大小写、非正则表达式的分割字符串
     * 
     * @param s
     * @param sub
     * @param trim
     *            加入前是否trim
     * @return
     */
    public static List<String> splitText(String s, String sub, boolean trim) {
        List<String> ret = new ArrayList<String>();
        if (s == null || sub == null) {
            return ret;
        }
        if (sub.length() == 0) {
            addToList(ret, s, trim);
            return ret;
        }

        while (s.length() > 0) {
            int i = indexOfText(s, sub);
            if (i == -1) {
                addToList(ret, s, trim);
                break;
            } else {
                addToList(ret, s.substring(0, i), trim);
                s = s.substring(i + sub.length());
            }
        }
        return ret;
    }

    /**
     * 非正则表达式的分割字符串
     * 
     * @param s
     * @param sub
     * @param trim
     *            加入前是否trim
     * @return
     */
    public static List<String> split(String s, String sub, boolean trim) {
        List<String> ret = new ArrayList<String>();
        if (s == null || sub == null) {
            return ret;
        }
        if (sub.length() == 0) {
            addToList(ret, s, trim);
            return ret;
        }

        while (s.length() > 0) {
            int i = s.indexOf(sub);
            if (i == -1) {
                addToList(ret, s, trim);
                break;
            } else {
                addToList(ret, s.substring(0, i), trim);
                s = s.substring(i + sub.length());
            }
        }
        return ret;
    }

    /**
     * 不区分大小写、非正则表达式的分割字符串
     * 
     * @param s
     * @param sub
     * @return
     */
    public static List<String> splitText(String s, String sub) {
        return splitText(s, sub, false);
    }

    /**
     * 非正则表达式的分割字符串
     * 
     * @param s
     * @param sub
     * @return
     */
    public static List<String> split(String s, String sub) {
        return split(s, sub, false);
    }

    public static String urlEncode(String s, String characterEncoding) {
        if (s == null || s.length() == 0) {
            return s;
        }
        try {
            s = java.net.URLEncoder.encode(s, characterEncoding);
        } catch (UnsupportedEncodingException silence) {
        }
        return s;
    }

    public static String urlDecode(String s, String characterEncoding) {
        if (s == null || s.length() == 0) {
            return s;
        }
        try {
            s = java.net.URLDecoder.decode(s, characterEncoding);
        } catch (UnsupportedEncodingException silence) {
        }
        return s;
    }

    private static class CharRanger {

        private char cmin;

        private char cmax;

        public CharRanger(String formater) {
            if (formater == null) {
                throw new NullPointerException();
            }

            String hint = "Thre char ranger [" + formater + "] is error! The right is: \"a-z\", \"1\"";
            if (formater.length() == 1) {
                cmin = formater.charAt(0);
                cmax = cmin;
            } else if (formater.length() == 3) {
                if (formater.charAt(1) != '-') {
                    throw new IllegalArgumentException(hint);
                }
                cmin = formater.charAt(0);
                cmax = formater.charAt(2);
                if (cmax < cmin) {
                    char c = cmax;
                    cmax = cmin;
                    cmin = c;
                }
            } else {
                throw new IllegalArgumentException(hint);
            }

        }

        public boolean isInRanger(char c) {
            return c >= cmin && c <= cmax;
        }

    }

    /**
     * 判断一个字符串中的字符是否都在[minChar, maxChar]范围, 注意null或length==0的字符串返回假<br>
     * 1) charsInRange("abc", "a-z") ==> true<br>
     * 2) charsInRange("abc123", "a-z") ==> false<br>
     * 3) charsInRange("abc123", "a-z", "0-9") ==> true<br>
     * 4) charsInRange("abc123", "a-z", "1", "2", "3") ==> true<br>
     * 
     * @param s
     * @param minChar
     * @param maxChar
     * @return
     */
    public static boolean in(String s, String... ranges) {
        if (s == null || s.length() == 0 || ranges.length == 0) {
            return false;
        }

        List<CharRanger> rangeList = new ArrayList<CharRanger>();
        for (String r : ranges) {
            rangeList.add(new CharRanger(r));
        }

        for (int i = 0, len = s.length(); i < len; i++) {
            boolean inIt = false;
            for (CharRanger ranger : rangeList) {
                if (ranger.isInRanger(s.charAt(i))) {
                    inIt = true;
                    break;
                }
            }
            if (!inIt) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个字符是否都在[minChar, maxChar]范围<br>
     * 1) charsInRange("a", "a-z") ==> true<br>
     * 2) charsInRange("1", "a-z") ==> false<br>
     * 3) charsInRange("1", "a-z", "0-9") ==> true<br>
     * 4) charsInRange("3", "a-z", "1", "2", "3") ==> true<br>
     * 
     * @param s
     * @param minChar
     * @param maxChar
     * @return
     */
    public static boolean in(char c, String... ranges) {
        List<CharRanger> rangeList = new ArrayList<CharRanger>();
        for (String r : ranges) {
            rangeList.add(new CharRanger(r));
        }

        boolean inIt = false;
        for (CharRanger ranger : rangeList) {
            if (ranger.isInRanger(c)) {
                inIt = true;
                break;
            }
        }
        if (!inIt) {
            return false;
        }
        return true;
    }

    /**
     * 是否为[0-9]|[a-z]|[A-Z]|[_]
     * 
     * @param c
     * @return
     */
    public static boolean isAlpha(char c) {
        if (c < MAPPER_LEN) {
            return charMapper[c].isAlpha;
        } else {
            return false;
        }
    }

    /**
     * 不区分大小写文本替换
     * 
     * @param s
     * @param pattern
     * @param rep
     * @return
     */
    public static String replaceText(String s, String pattern, String rep) {
        if (s == null || pattern == null) {
            return s;
        }
        rep = rep == null ? "" : rep;

        StringBuilder sbRet = new StringBuilder();
        while (s.length() > 0) {
            int i = indexOfText(s, pattern);
            if (i == -1) {
                sbRet.append(s);
                s = "";
            } else {
                sbRet.append(s.substring(0, i)).append(rep);
                s = s.substring(i + pattern.length());
            }
        }
        return sbRet.toString();
    }

    public static <T> T parseQueryValue(String url, T cond) {
        return parseQueryValue(url, "cond", cond);
    }

    /**
     * 从查询字符串中恢复查询值
     * 
     * @param url
     * @param cond
     */
    public static <T> T parseQueryValue(String url, String group, T cond) {
        if (STR.isBlank(url) || STR.isBlank(group) || cond == null) {
            return cond;
        }

        Map<String, Field> fields = new HashMap<String, Field>();
        Class<?> clazz = cond.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                fields.put(field.getName().toLowerCase(), field);
            }
            clazz = clazz.getSuperclass();
        }

        int i = url.indexOf("?");
        if (i > 0) {
            url = url.substring(i + 1);
        }

        List<String> list = STR.split(url, "&");
        for (String s : list) {
            if (!s.startsWith("$")) {
                continue;
            }
            s = s.substring(1);
            i = s.indexOf("=");
            if (i < 0) {
                continue;
            }

            String urlGroup = s.substring(0, i);
            if (group.equalsIgnoreCase(urlGroup)) {
                String value = s.substring(i + 1);
                updateQueryValueFields(cond, value, fields);
                break;
            }
        }
        return cond;
    }

    /**
     * 把XML属性的值，转换成boolean类型；如：1，y, t, true,yes都为真
     * 
     * @param attrValue
     * @return
     */
    public static boolean attrAsBool(String attrValue) {
        if (attrValue == null) {
            return false;
        }
        return STR.equalsAnyText(attrValue, "y", "true", "1", "yes", "ok", "t");
    }

    private static void updateQueryValueFields(Object cond, String value, Map<String, Field> fields) {
        for (String s : STR.split(value, "!")) {
            String propVal = s;
            int i = propVal.indexOf("-");
            String k = propVal.substring(0, i);
            String v = STR.decodeChars(propVal.substring(i + 1));
            try {
                Field field = fields.get(k.toLowerCase());
                if (field == null) {
                    continue;
                }

                field.setAccessible(true);
                Class<?> type = field.getType();
                if (type == int.class || type == Integer.class) {
                    field.set(cond, STR.toInteger(v));
                } else if (type == long.class || type == Long.class) {
                    field.set(cond, STR.toLong(v));
                } else if (type == double.class || type == Double.class) {
                    field.set(cond, STR.toDouble(v));
                } else if (type == BigInteger.class) {
                    field.set(cond, new BigInteger(v));
                } else if (type == BigDecimal.class) {
                    field.set(cond, new BigDecimal(v));
                } else if (type == boolean.class || type == Boolean.class) {
                    field.set(cond, "true".equalsIgnoreCase(v) || "on".equalsIgnoreCase(v));
                } else if (type == Date.class) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// TODO
                    field.set(cond, sdf.parse(v));
                } else if (type.isEnum()) {
                    Method valuesMethod = type.getMethod("values", new Class<?>[0]);
                    Object[] oEnums = (Object[]) valuesMethod.invoke(type);
                    for (Object o : oEnums) {
                        Enum<?> e = (Enum<?>) o;
                        if (e.name().equalsIgnoreCase(v)) {
                            field.set(cond, e);
                            break;
                        }
                    }
                } else {
                    field.set(cond, v);
                }
            } catch (Exception NULL) {
                // 因为页面条件可能被恶意修改，所以异常简单屏蔽
            }
        }
    }

    private static class CharMapper {

        boolean isAlpha;

        char upper;

        char lower;
    }

    private final static int MAPPER_LEN = 128;

    private final static CharMapper[] charMapper = new CharMapper[128];
    static {
        for (char c = 0; c < MAPPER_LEN; c++) {
            charMapper[c] = new CharMapper();
            charMapper[c].isAlpha = c <= 'Z' && c >= 'A' || c <= 'z' && c >= 'a' || c <= '9' && c >= '0' || c == '_';
            charMapper[c].upper = Character.toUpperCase(c);
            charMapper[c].lower = Character.toLowerCase(c);
        }

    }

    /**
     * 
     * 字符串缩略
     * 
     * @param str
     *            原字符串
     * @param width
     *            宽度
     * @param ellipsis
     *            省略符
     * @return 缩略字符
     */
    public static String abbreviate(String str, Integer width, String ellipsis) {
        if (width != null) {
            int strLength = 0;
            for (int strWidth = 0; strLength < str.length(); strLength++) {
                strWidth = Pattern.compile("[\\u4e00-\\u9fa5\\ufe30-\\uffa0]+$")
                        .matcher(String.valueOf(str.charAt(strLength))).find() ? strWidth + 2 : strWidth + 1;
                if (strWidth >= width) {
                    break;
                }
            }
            if (strLength < str.length()) {
                if (ellipsis != null) {
                    return str.substring(0, strLength + 1) + ellipsis;
                } else {
                    return str.substring(0, strLength + 1);
                }
            } else {
                return str;
            }
        } else {
            if (ellipsis != null) {
                return str + ellipsis;
            } else {
                return str;
            }
        }
    }

    /** 返回不区分大小比较器函数，参考 java.util.Comparator.comparing */
    public static <T> Comparator<T> fnCompareText(Function<? super T, String> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (c1, c2) -> {
            String s1 = NULL.nvl(keyExtractor.apply(c1));
            String s2 = NULL.nvl(keyExtractor.apply(c2));
            return s1.compareToIgnoreCase(s2);
        };
    }

    public final static char[] LETTERS = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', };

    /**
     * 随机生成n位大写字母组成的串
     * 
     * @param n
     *            生成随机串的位数(<=26)
     * @return
     */
    public static String randomStr(int n) {
        n = (n > 26) ? 26 : n;

        ThreadLocalRandom random = ThreadLocalRandom.current();
        Set<Character> chars = new HashSet<>();
        while (chars.size() < n) {
            chars.add(LETTERS[random.nextInt(26)]);
        }
        return CLLT.link(chars, "");
    }

    /**
     * 为原有url添加参数
     * 
     * @param url
     * @param key
     * @param val
     * @return
     */
    public static String addUrlParam(String url, String key, String val) {
        if (isBlank(url) || isBlank(key)) {
            return url;
        }

        key = trim(key);
        val = notBlank(val, "").trim();
        List<String> urls = split(url, "?");
        Map<String, String> args = new HashMap<>();
        if (urls.size() > 1) {
            List<String> argList = split(urls.get(1), "&");
            for (String s : argList) {
                int i = s.indexOf("=");
                if (i > -1) {
                    String k = s.substring(0, i).trim();
                    String v = s.substring(i + 1).trim();
                    if (!equalsText(k, key)) {
                        args.put(k, v);
                    }
                }
            }
        }
        args.put(key, val);

        StringBuilder ret = new StringBuilder(urls.get(0)).append("?");
        String and = "";
        for (String k : args.keySet()) {
            String v = args.get(k);
            ret.append(and).append(k).append("=").append(v);
            and = "&";
        }
        return ret.toString();
    }

    public static Boolean getSexFromIdNo(String cradId) {
        Boolean sex = true; // 默认为男
        if (!isBlank(cradId)) {
            int strLength = cradId.length();
            if (strLength == 18) { // 不计算15位旧身份证给默认男
                String str = cradId.substring(16, 17);
                if (Integer.parseInt(str) % 2 == 0) {
                    sex = false; // 性別为女
                }
            }
        }
        return sex;
    }

    /**
     * 根据传入参生成指定位数的随机码
     * 
     * @param number
     * @return
     */
    public static String getRandCode(Integer number) {
        Integer diffVal = (int) Math.pow(10, number - 1);
        Integer bound = (int) Math.pow(10, number) - diffVal - 1;
        return String.valueOf(new Random().nextInt(bound) + diffVal);
    }

    /**
     * 根据传入参生成指定位数的随机码,存入map
     * 
     * @param number
     * @return
     */
    public static Map<String, Object> getRandCode(Integer number, String name) {
        Map<String, Object> map = new HashMap<>();
        Integer diffVal = (int) Math.pow(10, number - 1);
        Integer bound = (int) Math.pow(10, number) - diffVal - 1;
        map.put(name, (String.valueOf(new Random().nextInt(bound) + diffVal)));
        return map;
    }

    public static String startZero(String s, int totalLen) {
        if (totalLen < 1) {
            throw new IllegalArgumentException("totalLen必须大于0");
        }
        if (s == null) {
            s = "";
        }

        StringBuilder sbRet = new StringBuilder();
        while (sbRet.length() + s.length() < totalLen) {
            sbRet.append("0");
        }
        sbRet.append(s);
        return sbRet.toString();
    }

    private static Set<Character> numNoChars = new HashSet<Character>(Arrays.asList('O', 'G', 'Z', 'V', 'U', 'L'));

    private static final Map<Integer, Character> numCharIndexes = new HashMap<>();

    private static final Map<Character, Integer> numChars = initNumChars();

    private static Map<Character, Integer> initNumChars() {
        Map<Character, Integer> ret = new HashMap<>();
        int inc = 0;
        for (char c = '0'; c <= '9'; c++) {
            int index = inc;
            ret.put(c, index);
            numCharIndexes.put(index, c);
            inc++;
        }

        for (char c = 'A'; c < 'Z'; c++) {
            if (!numNoChars.contains(c)) {
                int index = inc;
                numCharIndexes.put(index, c);
                ret.put(c, index);
                inc++;
            }
        }
        return ret;
    }

    public static String nextNum(String s, int totalLen) {
        if (isBlank(s)) {
            return startZero(s, totalLen);
        }
        s = s.toUpperCase();
        StringBuilder sbRet = new StringBuilder();
        sbRet.append("0").append(s);
        for (int i = sbRet.length() - 1; i > -1; i--) {
            char c = sbRet.charAt(i);
            Integer index = numChars.get(c);
            if (index == null) {
                sbRet.setCharAt(i, numCharIndexes.get(0));
                break;
            }

            index++;
            Character nc = numCharIndexes.get(index);
            if (nc != null) {
                sbRet.setCharAt(i, nc);
                break;
            } else {
                sbRet.setCharAt(i, numCharIndexes.get(0));
            }
        }

        String ret = startZero(sbRet.toString(), totalLen);
        if (ret.length() > totalLen && ret.charAt(0) == '0') {
            ret = ret.substring(1);
        }
        return ret;
    }

    public static char ranChar() {
        Random rand = new Random();
        int i = rand.nextInt(numChars.size());
        return numCharIndexes.get(i);
    }

    private static final int[] shuffleIndexes = { 38, 0, 27, 13, 18, 8, 3, 29, 34, 10, 48, 41, 45, 30, 44, 32, 33, 35,
            25, 37, 23, 36, 19, 31, 28, 40, 46, 21, 11, 5, 6, 39, 12, 49, 16, 7, 42, 22, 15, 47, 1, 9, 26, 43, 2, 14,
            17, 24, 20, 4 };

    /**
     * 正向打乱排序
     * 
     * @param s
     * @return
     */
    public static String shuffle(String s) {
        if (s == null || s.length() <= 1 || s.length() > shuffleIndexes.length) {
            return s;
        }

        // 定义一个数组，并将所有随机的50个数存放到数组中去
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < shuffleIndexes.length; i++) {
            int index = shuffleIndexes[i];
            if (index < s.length()) {
                indexes.add(index);
            }
        }

        /**
         * 定义一个STRING,将字符串传入并判断字符串的长度 利用setCharAt进行替换(将新的第i个下标设定为第i个字符的下标)
         */
        StringBuilder sbRet = new StringBuilder(s);
        for (int i = 0; i < s.length(); i++) {
            int newIndex = indexes.get(i);
            sbRet.setCharAt(newIndex, s.charAt(i));// 重新根据下标来重新排序
        }
        return shuffle2(sbRet.toString());
    }

    public static String unshuffle(String s) {
        if (s == null || s.length() <= 1 || s.length() > shuffleIndexes.length) {
            return s;
        }

        s = unshuffle2(s);

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < shuffleIndexes.length; i++) {
            int index = shuffleIndexes[i];
            if (index < s.length()) {
                indexes.add(index);
            }
        }

        StringBuilder sbRet = new StringBuilder(s);
        for (int newIndex = 0; newIndex < s.length(); newIndex++) {
            // 检索集合中
            int oldIndex = indexes.indexOf(newIndex);
            sbRet.setCharAt(oldIndex, s.charAt(newIndex));
        }
        return sbRet.toString();
    }

    private static String shuffle2(String s) {
        int i = _toInt(s);
        String lo = s.substring(0, i);
        String hi = s.substring(i);
        StringBuilder sb = new StringBuilder();
        if (i > s.length() / 2) {
            lo = _jump(lo, sb);
            hi = _swap(hi, sb);
        } else {
            lo = _swap(lo, sb);
            hi = _jump(hi, sb);
        }
        return hi + lo;
    }

    private static String unshuffle2(String s) {
        int i = _toInt(s);
        StringBuilder sb = new StringBuilder();
        String lo = s.substring(s.length() - i);
        String hi = s.substring(0, s.length() - i);
        if (i > s.length() / 2) {
            lo = _jump(lo, sb);
            hi = _swap(hi, sb);
        } else {
            lo = _swap(lo, sb);
            hi = _jump(hi, sb);
        }
        return lo + hi;

    }

    private static String _swap(String raw, StringBuilder sb) {
        sb.setLength(0);
        sb.append(raw);
        for (int lo = 0, hi = sb.length() - 1; lo < hi; lo++, hi--) {
            char clo = sb.charAt(lo);
            char chi = sb.charAt(hi);
            sb.setCharAt(lo, chi);
            sb.setCharAt(hi, clo);
        }
        return sb.toString();
    }

    private static String _jump(String raw, StringBuilder sb) {
        sb.setLength(0);
        sb.append(raw);
        for (int i = 0, len = sb.length(); i < len - 1; i += 2) {
            char c1 = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(i + 1));
            sb.setCharAt(i + 1, c1);
        }
        return sb.toString();
    }

    private static int _toInt(CharSequence s) {
        int ret = 0;
        int max = Integer.MAX_VALUE / 3;
        for (int i = s.length() - 1; i > -1; i--) {
            ret = ret + s.charAt(i);
            if (ret > max) {
                ret /= 3;
            }
        }
        return ret % s.length();
    }

}