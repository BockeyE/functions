package localThreadDemo;

import java.util.concurrent.locks.StampedLock;

public class StampedLocked {
}

class DataContainedStamped {
    int data;
    final StampedLock lock = new StampedLock();

    public DataContainedStamped(int i) {
        data = i;
    }

    public int read(int readTime) {
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)) {
            return data;
        }

        //验证失败，进行锁升级，从乐观读锁变成完整读锁
        try {
            stamp = lock.readLock();
            return data;
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public void write(int newData) {
        long stamp = lock.writeLock();
        try {
            data = newData;
        } finally {
            lock.unlockWrite(stamp);
        }
    }
}