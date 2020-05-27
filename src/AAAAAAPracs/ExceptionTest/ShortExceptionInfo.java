package AAAAAAPracs.ExceptionTest;

/**
 * @author bockey
 */
public class ShortExceptionInfo {
    public static void main(String[] args) {
//        logger.error(e.toString());
//        logger.error(e.getStackTrace()[0]);
        System.out.println(new Exception("").toString());
        System.out.println(new Exception("").getStackTrace()[0]);
    }
}
