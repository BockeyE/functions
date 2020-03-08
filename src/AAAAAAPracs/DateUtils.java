package AAAAAAPracs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期工具类
 *
 * @author bockey
 */
public final class DateUtils {

    public static final String SIMPLEDATEFORMAT_1 = "yyyyMMdd";
    public static final String SIMPLEDATEFORMAT_2 = "yyyy-MM-dd";
    public static final String SIMPLEDATEFORMAT_3 = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLEDATEFORMAT_4 = "yyyyMMddHHmmss";
    public static final String SIMPLEDATEFORMAT_5 = "yyyy年MM月dd日";

    public static final SimpleDateFormat sdf1 = new SimpleDateFormat(SIMPLEDATEFORMAT_1);
    public static final SimpleDateFormat sdf2 = new SimpleDateFormat(SIMPLEDATEFORMAT_2);
    public static final SimpleDateFormat sdf3 = new SimpleDateFormat(SIMPLEDATEFORMAT_3);
    public static final SimpleDateFormat sdf4 = new SimpleDateFormat(SIMPLEDATEFORMAT_4);
    public static final SimpleDateFormat sdf5 = new SimpleDateFormat(SIMPLEDATEFORMAT_5);


    /**
     * 把日期转换成format格式
     *
     * @param date
     * @return 整型
     */
    public static String dateFormat(java.util.Date date, String pattern) {
        switch (pattern) {
            case SIMPLEDATEFORMAT_2:
                return sdf2.format(date);
            case SIMPLEDATEFORMAT_3:
                return sdf3.format(date);
            case SIMPLEDATEFORMAT_1:
                return sdf1.format(date);
            case SIMPLEDATEFORMAT_4:
                return sdf4.format(date);
            case SIMPLEDATEFORMAT_5:
                return sdf5.format(date);
            default:
                return null;
        }
    }

    /**
     * 方法描述：根据pattern时间格式返回，strDate时间字符串对应的日期类型对象
     *
     * @param pattern 时间格式
     * @param strDate 时间字符串
     * @return DATE                日期对象
     */
    public static java.util.Date convertStringToDate(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 按天比较两个日期，忽略当天的时间
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 比较结果，日期2晚于日期1返回1，等于返回0，早于返回-1；
     */
    public static int compareByDate(java.util.Date date1, java.util.Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 > year2) {
            return -1;
        } else if (year1 < year2) {
            return 1;
        }

        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        return Integer.compare(day2, day1);

    }

    /**
     * 比较两个日期相差的天数
     *
     * @param date1 旧时间
     * @param date2 新时间
     * @return
     */
    public static int differentDays(java.util.Date date1, java.util.Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        if (year1 != year2) {//不同年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {//闰年
                    timeDistance += 366;
                } else {//不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {//同年
            return day2 - day1;
        }
    }
}