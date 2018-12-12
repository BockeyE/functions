package Sorts;

import java.util.Random;

public class RandomArrUtil {
    public static int[] getArr(int len, int max) {
        int[] ar = new int[len];
        Random random = new Random();
        for (int i = 0; i < ar.length; i++) {
            ar[i] = random.nextInt(max);
        }
        return ar;
    }
}
