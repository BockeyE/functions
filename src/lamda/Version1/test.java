package lamda.Version1;

import java.util.Comparator;

/**
 * @author bockey
 */
public class test {
    public static void main(String[] args) {
        Comparator<Integer> com1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
            }
        };

        //==================================================

        Comparator<Integer> com2 = (x, y) -> Integer.compare(x, y);
        Runnable r2 = () -> System.out.println("R2");

        //lamda 表达式，本质上是只包含需要的参数以及参数执行的功能。

    }
}
