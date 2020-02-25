package ProblemsEgs;

/**
 * @author bockey
 */
public class array {
    public static void main(String[] args) {
        String[] a = new String[2];
        Object[] b = a;
        a[0] = "hi";

        //array store exception
        b[1] = Integer.valueOf(42);
        for (Object o : b) {
            System.out.println(o);
        }
    }
}
