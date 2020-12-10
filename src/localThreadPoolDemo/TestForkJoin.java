package localThreadPoolDemo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TestForkJoin {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new LocalFJTask(5)));
    }
}

class LocalFJTask extends RecursiveTask<Integer> {
    int n;

    public LocalFJTask(int a) {
        n = a;
    }

    @Override
    public Integer compute() {
        if (n == 1) {
            return 1;
        } else {
            LocalFJTask task = new LocalFJTask(n - 1);
            //让一个线程去执行
            task.fork();
            //获取结果
            return n + task.join();
        }
    }
}