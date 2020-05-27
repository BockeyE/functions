package AutoID;


import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class IdGenUtils  {

    // chars
    public static final String[] _CHARS = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * Activiti ID 生成
     */
    public String getNextId() {
        return IdGenUtils.uuid();
    }

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = uuid();
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(_CHARS[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * 短8位UUID思想其实借鉴微博短域名的生成方式，但是其重复概率过高，而且每次生成4个，需要随即选取一个。
     * <p>
     * 本算法利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，然后通过模62操作，结果作为索引取出字符，
     *
     * @return String 返回8位不重复字符串
     */
    public static String generateShortUuid(String addKey, int find) {
        String key = generateShortUuid();
        if (!StringUtils.isEmpty(addKey)) {
            return String.join("", key, getRandom(addKey, find)).toLowerCase();
        }
        return key.toLowerCase();
    }

    /**
     * getRandom
     *
     * @param body String
     * @param leng int find count
     * @return String
     */
    public static String getRandom(String body, int leng) {
        if (StringUtils.isEmpty(body) || leng <= 0) {
            return "";
        }
        body = body.trim();
        if (leng == body.length()) {
            return body;
        }
        char[] chars = body.toCharArray();
        int size = body.length() - 1;
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < leng; i++) {
            sbr.append(chars[RandomUtils.nextInt(0, size)]);
        }
        return sbr.toString();
    }
}
