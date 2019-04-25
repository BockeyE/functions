package UTILLS.Tools.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证号码校验
 * 
 */
public class IDCardU {

    private static Map<String, String> city = new HashMap<String, String>();
    static {
        city.put("11", "北京");
        city.put("12", "天津");
        city.put("13", "河北");
        city.put("14", "山西");
        city.put("15", "内蒙古");
        city.put("21", "辽宁");
        city.put("22", "吉林");
        city.put("23", "黑龙江");
        city.put("31", "上海");
        city.put("32", "江苏");
        city.put("33", "浙江");
        city.put("34", "安徽");
        city.put("35", "福建");
        city.put("36", "江西");
        city.put("37", "山东");
        city.put("41", "河南");
        city.put("42", "湖北");
        city.put("43", "湖南");
        city.put("44", "广东");
        city.put("45", "广西");
        city.put("46", "海南");
        city.put("50", "重庆");
        city.put("51", "四川");
        city.put("52", "贵州");
        city.put("53", "云南");
        city.put("54", "西藏");
        city.put("61", "陕西");
        city.put("62", "甘肃");
        city.put("63", "青海");
        city.put("64", "宁夏");
        city.put("65", "新疆");
        city.put("71", "台湾");
        city.put("81", "香港");
        city.put("82", "澳门");
        city.put("91", "国外");
    }

    /**
     * 身份证号码校验
     * 
     * @param code
     *            身份证号码
     * @return
     */
    public static boolean validate(String code) {
        if (STR.isBlank(code)) {
            return false;
        }
        if (code.length() != 18) {
            return false;
        }
        boolean pass = true;

        Pattern p = Pattern.compile("^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|X)$");
        Matcher m = p.matcher(code);
        if (!m.matches()) {
            pass = false;
        } else if (null == city.get(code.substring(0, 2))) {
            pass = false;
        } else {
            // 18位身份证需要验证最后一位校验位
            if (code.length() == 18) {
                String[] codes = code.split("");// 19位
                // ∑(ai×Wi)(mod 11)
                // 加权因子
                Integer[] factor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
                // 校验位
                // Integer[] parity = { 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
                String[] parity = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
                int sum = 0;
                int ai = 0;
                int wi = 0;
                for (int i = 0; i < 17; i++) {
                    ai = Integer.valueOf(codes[i + 1]);
                    wi = factor[i];
                    sum += ai * wi;
                }

                if (!codes[18].equalsIgnoreCase(parity[sum % 11])) {
                    pass = false;
                }
            }
        }
        return pass;
    }
}
