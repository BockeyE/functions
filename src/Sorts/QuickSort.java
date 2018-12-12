package Sorts;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = RandomArrUtil.getArr(5, 8);
        System.out.println(Arrays.toString(arr));
        System.out.println("=========");

        QuickSort qs = new QuickSort();
        qs.quick(arr);
        System.out.println(Arrays.toString(arr));
    }


    public void quick(int[] arr) {
        quicks(arr, 0, arr.length - 1);
    }

    public void quicks(int[] arr, int head, int end) {
        if (head < end) {
            int piv = quickCore(arr, head, end);
            quicks(arr, head, piv);
            quicks(arr, piv + 1, end);
        }
    }


    //    注意这里循环过程中，arr[x]与piv的对比，要加=，
    //    否则在随机数列中有重复数字时会陷入无限递归导致overflow
    //    即，如果2个循环都是不加=，则循环会空转，无法停止
    //    只有一个=时，会在相同数字时overflow，2个都加=时会一切正常。
    //    原因是在分解递归过程中，如果有两个相同的数字，那么在进入arr=[1,1]，这样的情况时
    //    依然会进入方法，然后在判断之后，第一个while不进入，第二个while会进入然后head+1，这时head 和end 相等，
    //    然后继续执行位置交换赋值，导致无限递归。
    //    如果都没有=时，2个循环都不会进入，head保持小于end，while会空转
    //    都有=时，都会进入，此时head > end， 然后跳出循环，到达递归终点。
    public int quickCore(int[] arr, int head, int end) {
        int piv = arr[head];
        while (head < end) {
            while (head < end && (arr[end] >= piv)) {
                end--;
            }
            arr[head] = arr[end];
            while (head < end && (arr[head] <= piv)) {
                head++;
            }
            arr[end] = arr[head];
        }
        arr[head] = piv;
        return head;
    }
}
