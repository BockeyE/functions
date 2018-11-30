package commomtest;


import java.util.Comparator;

public class test {
    int[] a = {1, 2, 3, 4, 5};

    public static void main(String[] args) {
        test test = new test();
        test.com((Object o1, Object o2) -> {
            {
                return (Integer) o1 - (Integer) o2;
            }
        });

        System.out.println(test.a);

    }

    public int[] com(Comparator com) {

        for (int i : a) {
            if (com.compare(i, 0) > 0)
                continue;
        }


        return a;
    }
}
