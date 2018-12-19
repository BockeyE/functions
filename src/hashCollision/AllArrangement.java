package hashCollision;


import java.util.ArrayList;
import java.util.List;

/**
 * @author bockey
 * un repeatable arrangement
 */
public class AllArrangement {
    public static void main(String[] args) {

        char[] ss = {'a', 'b', 'c', 'd'};
        nosort(ss);

    }

    public static void unrepeat(char[] ss) {
        unrepeatSub(ss, 0);
    }

    public static void unrepeatSub(char[] ss, int i) {
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
                unrepeatSub(ss, i + 1);
                temp = ss[j];
                ss[j] = ss[i];
                ss[i] = temp;
            }
        }
    }

    public static void nosort(char[] chars) {
        if (chars == null || chars.length == 0) {
            return;
        }
        List<Character> list = new ArrayList<>();
        for (int i = 0; i <= chars.length; i++) {
            nosortSub(chars, 0, i, list);
        }

    }

    public static void nosortSub(char[] chars, int begin, int num, List<Character> list) {
        if (num == 0) {
            System.out.println(list);
            return;
        }
        if (begin == chars.length) {
            return;
        }
        list.add(chars[begin]);
        nosortSub(chars, begin + 1, num - 1, list);
        list.remove((Character) chars[begin]);
        nosortSub(chars, begin + 1, num, list);
    }
}
