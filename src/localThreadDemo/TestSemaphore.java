package localThreadDemo;

import java.util.concurrent.Semaphore;

public class TestSemaphore {
    public static void main(String[] args) {
//        限制上限3
        Semaphore s = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    try {
                        s.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    s.release();
                }
            }).start();
        }
    }
}
