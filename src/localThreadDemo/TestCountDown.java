package localThreadDemo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class TestCountDown {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        new Thread(() -> {
            latch.countDown();
        }).start();
        new Thread(() -> {
            latch.countDown();
        }).start();
        new Thread(() -> {
            latch.countDown();
        }).start();
        new Thread(() -> {
            latch.countDown();
        }).start();

        latch.await();


        CyclicBarrier cb = new CyclicBarrier(3, () -> {
            System.out.println("print ended");
        });
        new Thread(() -> {
            try {
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
