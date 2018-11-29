package RSAdemo;

import java.util.ArrayList;
import java.util.List;


public class RSAdemo {

    private Integer q;
    private Integer p;
    private Integer e;
    private String X;
    private String Y;
    private Integer m;
    private Integer d;
    private Integer n;

    public RSAdemo(Integer q, Integer p, Integer e, Integer d) {
        this.q = q;
        this.p = p;
        this.e = e;
        this.d = d;
        n = p * q;
        m = (p - 1) * (q - 1);
        checkValid();
    }

    private void checkValid() {

        if (e != ((p - 1) * (q - 1))) {
            System.out.println(" e was not valid");
            System.out.println("please change e");
        }
        if ((d * e) % ((p - 1) * (q - 1)) != 1) {
            System.out.println(" d was not valid");
            System.out.println("please change d");
        }

    }

    public void showKeys() {
        System.out.println(
                " n : " + (q * p)
        );
    }

    public Object[] encodeByinfo(int[] x) {
        List arr = new ArrayList();
        for (int a : x) {
            arr.add(encoreCode(a));
        }
        return (arr.toArray());

    }

    private int encoreCode(int c) {
        int a = (int) Math.pow(c, e);
        a = a % n;
        return (char) a;
    }

    public Object[] decodeByinfo(Object[] x) {

        List arr = new ArrayList();
        for (Object a : x) {
            arr.add(decoreCode((Integer) a));
        }

        return (arr.toArray())
                ;
    }

    private int decoreCode(int c) {
        int a = (int) Math.pow(c, d);
        a = a % n;
        return a;
    }
}
