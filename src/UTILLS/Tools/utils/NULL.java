package UTILLS.Tools.utils;

import java.math.BigDecimal;

/**
 * 专门处理NULL值工具类
 * 
 * @author PanJun
 * 
 */
public final class NULL {

    public static boolean not(Object o) {
        return o != null;
    }

    public static boolean nvl(Boolean oldValue) {
        return nvl(oldValue, false);
    }

    public static boolean nvl(Boolean oldValue, boolean defaultValue) {
        return oldValue == null ? defaultValue : oldValue;
    }

    public static short nvl(Short oldValue) {
        return nvl(oldValue, (short) 0);
    }

    public static short nvl(Short oldValue, short defaultValue) {
        return oldValue == null ? defaultValue : oldValue;
    }

    public static int nvl(Integer oldValue) {
        return nvl(oldValue, 0);
    }

    public static int nvl(Integer oldValue, int defaultValue) {
        return oldValue == null ? defaultValue : oldValue;
    }

    public static long nvl(Long oldValue) {
        return nvl(oldValue, 0l);
    }

    public static long nvl(Long oldValue, long defaultValue) {
        return oldValue == null ? defaultValue : oldValue;
    }

    public static float nvl(Float oldValue) {
        return nvl(oldValue, (float) 0);
    }

    public static float nvl(Float oldValue, float defaultValue) {
        return oldValue == null ? defaultValue : oldValue;
    }

    public static double nvl(Double oldValue) {
        return nvl(oldValue, (double) 0);
    }

    public static double nvl(Double oldValue, double defaultValue) {
        return oldValue == null ? defaultValue : oldValue;
    }

    private static BigDecimal BD_ZERO = new BigDecimal("0");

    public static BigDecimal nvl(BigDecimal oldValue) {
        return nvl(oldValue, BD_ZERO);
    }

    public static BigDecimal nvl(BigDecimal oldValue, BigDecimal defaultValue) {
        return oldValue == null ? defaultValue : oldValue;
    }

    public static String nvl(String oldValue) {
        return nvl(oldValue, "");
    }

    public static String nvl(String oldValue, String defaultValue) {
        return oldValue == null ? defaultValue : oldValue;
    }

    public static <T> T nvl(T oldValue, T defaultValue) {
        return oldValue == null ? defaultValue : oldValue;
    }

}