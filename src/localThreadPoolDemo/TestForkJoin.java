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

class LocalFJTask2 extends RecursiveTask<Integer> {
    int begin;
    int end;

    public LocalFJTask2(int a, int b) {
        begin = a;
        end = b;
    }

    @Override
    public Integer compute() {
        if (begin == end) {
            return begin;
        } else if (end - begin == 1) {
            return end + begin;
        } else {
            int mid = (begin + end) / 2;
            LocalFJTask2 task1 = new LocalFJTask2(begin, mid);
            task1.fork();
            LocalFJTask2 task2 = new LocalFJTask2(mid + 1, end);
            //让一个线程去执行
            task2.fork();
            //获取结果
            return task1.join() + task2.join();
        }
    }
}