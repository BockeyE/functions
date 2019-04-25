package UTILLS.Tools.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * 日期工具类
 * 
 * @author PanJun
 * 
 */
public final class DATE {

    private static ThreadLocal<Calendar> calInstance = new ThreadLocal<Calendar>() {

        protected Calendar initialValue() {
            return Calendar.getInstance();
        }
    };

    /**
     * 把日期加天数
     * 
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        if (date == null)
            return null;

        Calendar calendar = calInstance.get();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 把日期加天数，并清除时间部分
     * 
     * @param date
     * @param day
     * @return
     */
    public static Date addDayNoTime(Date date, int day) {
        return clearTime(addDay(date, day));
    }

    /**
     * 把日期加上指定秒数，返回结果
     * 
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSecond(Date date, int seconds) {
        if (date == null)
            return null;

        Calendar calendar = calInstance.get();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 把日期加上指定分钟数，返回结果
     * 
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMin(Date date, int minutes) {
        if (date == null)
            return null;

        Calendar calendar = calInstance.get();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 把日期加上指定分钟数，返回结果
     * 
     * @param date
     * @param hours
     * @return
     */
    public static Date addHour(Date date, int hours) {
        if (date == null)
            return null;

        Calendar calendar = calInstance.get();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 请参看Calender.get(field)函数
     * 
     * @param date
     * @param field
     * @return
     */
    public static int get(Date date, int field) {
        if (date == null) {
            return -1;
        }

        Calendar cal = calInstance.get();
        cal.setTime(date);
        return cal.get(field);
    }

    /**
     * 把日期转换成yyyyMMddHH格式
     * 
     * @param date
     * @return 整型
     */
    public static Integer toYmdh(Date date) {
        if (date != null) {
            return Integer.parseInt(new SimpleDateFormat("yyyyMMddHH").format(date));
        } else {
            return null;
        }
    }

    /**
     * 把日期转换成yyyyMMdd格式
     * 
     * @param date
     * @return 整型
     */
    public static Integer toYmd(Date date) {
        if (date != null) {
            return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(date));
        } else {
            return null;
        }
    }

    /**
     * 把日期转换成yyyyMMddHHmmss格式
     * 
     * @param date
     * @return 整型
     */
    public static Long toLongYmd(Date date) {
        if (date != null) {
            return Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
        } else {
            return null;
        }
    }

    /**
     * 把日期转换成yyyyMM格式
     * 
     * @param date
     * @return 整型
     */
    public static Integer toYm(Date date) {
        if (date != null) {
            return Integer.parseInt(new SimpleDateFormat("yyyyMM").format(date));
        } else {
            return null;
        }
    }

    /**
     * 把日期转换成yyyyMMddHH格式
     * 
     * @param date
     * @return 长整型
     */
    public static Long toLongYmdh(Date date) {
        if (date != null) {
            return Long.parseLong(new SimpleDateFormat("yyyyMMddHH").format(date));
        } else {
            return null;
        }
    }

    /**
     * 把Ym转换成日期
     * 
     * @param numYm
     * @return
     */
    public static Date fromYm(Number numYm) {
        if (numYm == null) {
            return null;
        }
        int ym = numYm.intValue();
        int y = ym / 100;
        int m = ym - (ym / 100) * 100;
        Calendar calendar = calInstance.get();
        calendar.clear();
        calendar.set(Calendar.YEAR, y);
        calendar.set(Calendar.MONTH, m - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 把Ymd转换成日期
     * 
     * @param numYmd
     * @return
     */
    public static Date fromYmd(Number numYmd) {
        if (numYmd == null) {
            return null;
        }
        return fromYmdh(numYmd.intValue() * 100);
    }

    /**
     * 把Ymdh转换成日期
     * 
     * @param numYmd
     * @return
     */
    public static Date fromYmdh(Number numYmdh) {
        if (numYmdh == null) {
            return null;
        }
        int ymd = numYmdh.intValue();
        int h = ymd - (ymd / 100) * 100;
        ymd = ymd / 100;
        int d = ymd - (ymd / 100) * 100;
        ymd = ymd / 100;
        int m = ymd - (ymd / 100) * 100;
        int y = ymd / 100;
        Calendar calendar = calInstance.get();
        calendar.clear();
        calendar.set(Calendar.YEAR, y);
        calendar.set(Calendar.MONTH, m - 1);
        calendar.set(Calendar.DAY_OF_MONTH, d);
        calendar.set(Calendar.HOUR_OF_DAY, h);
        return calendar.getTime();
    }

    /**
     * 把日期加月份
     * 
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date, int month) {
        if (date == null)
            return null;

        Calendar calendar = calInstance.get();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 把日期加年份
     * 
     * @param date
     * @param month
     * @return
     */
    public static Date addYear(Date date, int year) {
        if (date == null)
            return null;

        Calendar calendar = calInstance.get();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 把日期的时分秒去除只留年月日
     * 
     * @param date
     * @return 只留年月日的日期
     */
    public static Date clearTime(Date date) {
        if (date == null)
            return null;

        Calendar calendar = calInstance.get();
        calendar.setTime(date);
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.set(Calendar.YEAR, y);
        calendar.set(Calendar.MONTH, m);
        calendar.set(Calendar.DAY_OF_MONTH, d);
        return calendar.getTime();
    }

    /**
     * d1日期部分是否大于d2
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static boolean dayGt(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        d1 = clearTime(d1);
        d2 = clearTime(d2);
        return d1.getTime() > d2.getTime();
    }

    /**
     * d1日期部分是否大于等于d2
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static boolean dayGte(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        d1 = clearTime(d1);
        d2 = clearTime(d2);
        return d1.getTime() >= d2.getTime();
    }

    /**
     * 设置日期格式的时间部分包含：时、分、秒；如果某部分为负数，不修改此部分
     * 
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date setTime(Date date, int hour, int minute, int second) {
        Calendar cal = calInstance.get();
        cal.setTime(date);
        if (hour >= 0) {
            cal.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute >= 0) {
            cal.set(Calendar.MINUTE, minute);
        }
        if (second >= 0) {
            cal.set(Calendar.SECOND, second);
        }
        return cal.getTime();
    }

    /**
     * 日期转化为字串
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToStr(Date date, String pattern) {
        if (date == null || pattern == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 字串转化成日期
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static Date strToDate(String date, String pattern) {
        if (date == null || pattern == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回两个日期之间的天数差, 不计算时/分/秒<br>
     * 如：d1=2018-01-01 00:01 d2=2018-01-02 23:59 --> return 1;
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Integer diffDay(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return null;
        }
        d1 = clearTime(d1);
        d2 = clearTime(d2);
        long diff = TimeUnit.MILLISECONDS.toDays(d1.getTime()) - TimeUnit.MILLISECONDS.toDays(d2.getTime());
        return (int) diff;
    }

    /**
     * 返回两个日期之间的小时数
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Long diffHour(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return null;
        }

        return TimeUnit.MILLISECONDS.toHours(d1.getTime() - d2.getTime());
    }

    /**
     * 返回两个日期之间的分钟数
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Long diffMin(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return null;
        }

        return TimeUnit.MILLISECONDS.toMinutes(d1.getTime() - d2.getTime());
    }

    /**
     * 返回两个日期之间的秒数
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static Long diffSec(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return null;
        }

        return TimeUnit.MILLISECONDS.toSeconds(d1.getTime() - d2.getTime());
    }

    /**
     * 取得当前日期是一年中的多少周，一周的开始是周日
     * 
     * @param date
     * @return
     */
    public static int weekOfYear(Date date) {
        Calendar c = calInstance.get();
        // 一周的开始时间为周日
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 得到某一年周的总数
     * 
     * @param year
     * @return
     */
    public static int allWeeksInYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return weekOfYear(c.getTime());
    }

    /**
     * 兼容多种日期格式的字符串，将其转换为日期 兼容格式：yyyy-mm-dd,yyyymmdd,yyyy/mm/dd
     * 
     * @param birthday
     * @return
     */
    public static Date parseToDate(String birthday) {
        if (STR.isBlank(birthday)) {
            return null;
        }
        Date date = null;
        try {
            date = strToDate(birthday, "yyyy-MM-dd");
        } catch (RuntimeException e1) {
            try {
                date = strToDate(birthday, "yyyyMMdd");
            } catch (RuntimeException e2) {
                date = strToDate(birthday, "yyyy/MM/dd");
            }
        }
        return date;
    }

    /**
     * 取一堆时间中最小
     * 
     * @param dates
     * @return
     */
    public static Date min(Date... dates) {
        Date ret = null;
        for (Date d : dates) {
            if (d != null) {
                if (ret == null) {
                    ret = d;
                } else if (ret.getTime() > d.getTime()) {
                    ret = d;
                }
            }
        }
        return ret;
    }

    /**
     * 取一堆时间中最大
     * 
     * @param dates
     * @return
     */
    public static Date max(Date... dates) {
        Date ret = null;
        for (Date d : dates) {
            if (d != null) {
                if (ret == null) {
                    ret = d;
                } else if (ret.getTime() < d.getTime()) {
                    ret = d;
                }
            }
        }
        return ret;
    }

}