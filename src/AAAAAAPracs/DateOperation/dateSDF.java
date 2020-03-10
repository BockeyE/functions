package AAAAAAPracs.DateOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author bockey
 */
public class dateSDF {
    public static void main(String[] args) throws ParseException {
        //        2020-03-06T03:15:15.000+0000
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        System.out.println(sdf.parse("time"));
    }
}
