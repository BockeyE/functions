package hashCollision;


/**
 * @author bockey
 */
public class AllArrangement {
    public static void main(String[] args) {

        char[] ss = {'a', 'b', 'c','d'};
        tset(ss, 0);

    }

    public static void tset(char[] ss, int i) {
        if (ss == null || i < 0 || i > ss.length) {
            return;
        }
        if (i == ss.length) {
            System.out.println(new String(ss));
        } else {
            for (int j = i; j < ss.length; j++) {
                char temp = ss[j];
                ss[j] = ss[i];
                ss[i] = temp;
                tset(ss, i + 1);
                temp = ss[j];
                ss[j] = ss[i];
                ss[i] = temp;
            }
        }
    }
}
