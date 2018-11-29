package RSAdemo;

public class test {
    public static void main(String[] args) {
        RSAdemo r1 = new RSAdemo(5, 7, 5, 5);
        int[] arr = {2, 3, 4, 5, 6};
        Object[] hello = r1.encodeByinfo(arr);
        System.out.println("encoded message: ");
        for (Object o : hello) {
            System.out.print(o + " ");
        }
        System.out.println();
        Object[] s = r1.decodeByinfo(hello);
        System.out.println("decoded message from above: ");
        for (Object o : s) {
            System.out.print(o + " ");
        }

    }
}
