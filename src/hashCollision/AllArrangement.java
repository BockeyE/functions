package hashCollision;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author bockey
 * un repeatable arrangement
 */
public class AllArrangement {
    public static void main(String[] args) {

        String[] ss = {"a", "b", "c", "d"};
        unrepeat(ss);

    }

    public static void unrepeat(String[] ss) {
        unrepeatSub(ss, 0);
    }

    public static void unrepeatSub(String[] ss, int i) {
        if (ss == null || i < 0 || i > ss.length) {
            return;
        }
        if (i == ss.length) {
            System.out.println(Arrays.toString(ss));
        } else {
            for (int j = i; j < ss.length; j++) {
                String temp = ss[j];
                ss[j] = ss[i];
                ss[i] = temp;
                unrepeatSub(ss, i + 1);
                temp = ss[j];
                ss[j] = ss[i];
                ss[i] = temp;
            }
        }
    }

    public static void nosort(String[] chars) {
        if (chars == null || chars.length == 0) {
            return;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= chars.length; i++) {
            nosortSub(chars, 0, i, list);
        }

    }

    public static void nosortSub(String[] chars, int begin, int num, List<String> list) {
        if (num == 0) {
            System.out.println(list);
            return;
        }
        if (begin == chars.length) {
            return;
        }
        list.add(chars[begin]);
        nosortSub(chars, begin + 1, num - 1, list);
        list.remove((String) chars[begin]);
        nosortSub(chars, begin + 1, num, list);
    }
}
