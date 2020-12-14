package localThreadDemo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LocalThreadPoolTest {
}

class LocalThreadPool {
    private LocalBlockingQueue<Runnable> taskQueue;

    private HashSet<LocalWorker> workers = new HashSet<>();

    private int coreSize;

    private long timeout;

    private TimeUnit timeUnit;

    private LocalRejectPolicy<Runnable> rejectPolicy;

    public LocalThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int cap, LocalRejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new LocalBlockingQueue<>(cap);
        this.rejectPolicy = rejectPolicy;
    }


    public void executeTask(Runnable task) {
        synchronized (workers) {
            if (workers.size() < coreSize) {
                LocalWorker worker = new LocalWorker(task);
                workers.add(worker);
                worker.start();
            } else {
                taskQueue.put(task);
            }
        }
    }


    class LocalWorker extends Thread {
        private Runnable task;

        public LocalWorker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            //take会阻塞，适合 线程池 阻塞策略
            while (task != null || (task = taskQueue.take()) != null) {
                //poll会阻塞特定时间，适合 线程池 超时时间策略
//            while (task != null || (task = taskQueue.poll(timeout,timeUnit)) != null) {
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                workers.remove(this);
            }

        }
    }
}

@FunctionalInterface
interface LocalRejectPolicy<T> {
    void reject(LocalBlockingQueue<T> queue, T task);
}

class LocalBlockingQueue<T> {
    private Deque<T> queue = new ArrayDeque<>();

    private ReentrantLock lock = new ReentrantLock();

    private Condition fullWaitSet = lock.newCondition();

    private Condition emptyWaitSet = lock.newCondition();

    private int capacity;


    public LocalBlockingQueue() {
        this(5);
    }

    public LocalBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    //    带超时的阻塞获取
    public T poll(long timeout, TimeUnit timeUnit) {
        lock.lock();
        long nanos = timeUnit.toNanos(timeout);
        try {
            while (queue.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        return null;
                    }
                    //返回值是等待的剩余时间
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    //    带超时时间的阻塞添加
    public boolean offer(T t, long timeout, TimeUnit timeUnit) {
        lock.lock();
        long nanos = timeUnit.toNanos(timeout);
        try {
            while (queue.size() == capacity) {
                try {
                    if (nanos <= 0) {
                        return false;
                    }
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(t);
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    //    阻塞获取
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    //    阻塞添加
    public void put(T t) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(t);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return capacity;
        } finally {
            lock.unlock();
        }
    }


    public void tryPut(LocalRejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            if (queue.size() == capacity) {
                rejectPolicy.reject(this, task);
            } else {
                queue.addLast(task);
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }
    }

}