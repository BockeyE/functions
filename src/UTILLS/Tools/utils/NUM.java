package UTILLS.Tools.utils;

import java.math.BigDecimal;

public class NUM {

    /** 判断相等 */
    public static boolean eq(double d1, double d2) {
        double delta = d1 - d2;
        if (delta < 0) {
            delta = -delta;
        }
        return delta < 0.00001;
    }

    /** 判断相等 */
    public static boolean eq(BigDecimal d1, BigDecimal d2) {
        if (d1 == d2) {
            return true;
        }
        return d1 != null && d2 != null && d1.compareTo(d2) == 0;
    }

    /** 判断小于 */
    public static boolean lt(BigDecimal d1, BigDecimal d2) {
        return d1 != null && d2 != null && d1.compareTo(d2) < 0;
    }

    /** 判断小于等于 */
    public static boolean lte(double d1, double d2) {
        return d1 < d2 || eq(d1, d2);
    }

    /** 判断小于等于 */
    public static boolean lte(BigDecimal d1, BigDecimal d2) {
        return d1 != null && d2 != null && d1.compareTo(d2) <= 0;
    }

    /** 判断大于 */
    public static boolean gt(BigDecimal d1, BigDecimal d2) {
        return d1 != null && d2 != null && d1.compareTo(d2) > 0;
    }

    /** 判断大于等于 */
    public static boolean gte(double d1, double d2) {
        return d1 > d2 || eq(d1, d2);
    }

    /** 判断大于等于 */
    public static boolean gte(BigDecimal d1, BigDecimal d2) {
        return d1 != null && d2 != null && d1.compareTo(d2) >= 0;
    }

    /** 最小值 */
    public static BigDecimal min(BigDecimal... bds) {
        BigDecimal ret = null;
        for (BigDecimal b : bds) {
            if (b != null && (ret == null || b.compareTo(ret) < 0)) {
                ret = b;
            }
        }
        return ret;
    }

    /** 最大值 */
    public static BigDecimal max(BigDecimal... bds) {
        BigDecimal ret = null;
        for (BigDecimal b : bds) {
            if (b != null && (ret == null || b.compareTo(ret) > 0)) {
                ret = b;
            }
        }
        return ret;
    }

    /** 向上设置精度 */
    public static BigDecimal roundUp(Number n, int scale) {
        BigDecimal ret = new BigDecimal(n.doubleValue());
        ret = ret.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return ret;
    }

    public static boolean isZero(BigDecimal v) {
        return v != null && v.compareTo(BigDecimal.ZERO) == 0;
    }

    /** 正数 */
    public static boolean isPlus(BigDecimal v) {
        return v != null && v.compareTo(BigDecimal.ZERO) > 0;
    }

    /** 负数 */
    public static boolean isMinus(BigDecimal v) {
        return v != null && v.compareTo(BigDecimal.ZERO) < 0;
    }

}
