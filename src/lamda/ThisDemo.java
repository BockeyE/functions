package lamda;



/**
 * @author bockey
 */
public class ThisDemo {
    private String name = "ThisDemo";

    public void test() {
        new Thread(new Runnable() {
            private String name = "runnable";

            @Override
            public void run() {
                System.out.println("current this is : " + this.name);
            }
        }).start();
        new Thread(() -> {
            System.out.println(" current this is : " + this.name
            );
        }).start();
    }

    public static void main(String[] args) {
         new ThisDemo().test();
    }
}
