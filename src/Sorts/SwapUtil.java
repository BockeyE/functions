package Sorts;

public class SwapUtil {
    public static int[] swap(int[] arr, int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
        return arr;
    }
}
