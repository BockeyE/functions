package AAAAAAPracs.DateOperation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author bockey
 */
public class DateOperation {
    public static void main(String[] args) {
        Date date = new Date();//取时间
        System.out.println(date.toString());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.YEAR, 1);//把日期往后增加一年.整数往后推,负数往前移动
        calendar.add(calendar.DAY_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(calendar.WEEK_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        date = calendar.getTime();   //这个时间就是日期往后推一天的结果
        System.out.println(date.toString());

    }
}
