package SnowFlakeDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *  Java 实现的SnowFlake生成UUID （Java代码实战-007）
 *
 *  SnowFlake所生成的ID一共分成四部分：
 *
 * 1.第一位
 * 占用1bit，其值始终是0，没有实际作用。
 *
 * 2.时间戳
 * 占用41bit，精确到毫秒，总共可以容纳约69 年的时间。
 *
 * 3.工作机器id
 * 占用10bit，其中高位5bit是数据中心ID（datacenterId），低位5bit是工作节点ID（workerId），做多可以容纳1024个节点。
 *
 * 4.序列号
 * 占用12bit，这个值在同一毫秒同一节点上从0开始不断累加，最多可以累加到4095。
 *
 * SnowFlake算法在同一毫秒内最多可以生成多少个全局唯一ID呢？只需要做一个简单的乘法：
 *
 * 同一毫秒的ID数量 = 1024 X 4096 = 4194304
 *
 * 这个数字在绝大多数并发场景下都是够用的。

 */


public class SnowFlakeIdGenerator {
    // The initial time(2017-01-01)
    private static final long INITIAL_TIME_STAMP = 1483200000000L;

    // Machine ID bits
    private static final long WORK_ID_BITS = 5L;

    // DataCenter ID bits
    private static final long DATACENTER_ID_BITS = 5L;

    // The maximum machine ID supported is 31, which can quickly calculate the maximum decimal number that a few binary numbers can represent.
    private static final long MAX_WORKER_ID = ~(-1L << WORK_ID_BITS);

    // The maximum datacenter ID supported is 31
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    // Sequence ID bits
    private static final long SEQUENCE_BITS = 12L;

    // The machine ID offset,12
    private static final long WORKERID_OFFSET = SEQUENCE_BITS;

    // The datacent ID offset,12+5
    private static final long DATACENTERID_OFFSET = WORK_ID_BITS + SEQUENCE_BITS;

    // The timestape offset, 5+5+12
    private static final long TIMESTAMP_OFFSET = DATACENTER_ID_BITS + WORK_ID_BITS + SEQUENCE_BITS;

    // Sequence mask,4095
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    // Worker ID ,0~31
    private long workerId;

    // Datacenter ID,0~31
    private long datacenterId;

    // Sequence,0~4095
    private long sequence = 0L;

    // Last timestamp
    private long lastTimestamp = -1L;

    public SnowFlakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0)
            throw new IllegalArgumentException(String.format("WorkerID can't be greater than %d or less than 0", MAX_WORKER_ID));
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0)
            throw new IllegalArgumentException(String.format("DataCenterID can't be greater than %d or less than 0", MAX_WORKER_ID));
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timeStamp = System.currentTimeMillis();
        if (timeStamp < lastTimestamp)
            throw new RuntimeException("The current time less than last time");
        if (timeStamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (0 == sequence)
                timeStamp = tillNextMillis(lastTimestamp);
        } else {
            sequence = 0L;
        }
        lastTimestamp = timeStamp;

        return (timeStamp - INITIAL_TIME_STAMP) << TIMESTAMP_OFFSET | (datacenterId << DATACENTERID_OFFSET) | (workerId << WORKERID_OFFSET) | sequence;
    }

    private long tillNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp)
            timestamp = System.currentTimeMillis();
        return timestamp;
    }

    public static void main(String[] args) {
        SnowFlakeIdGenerator generator = new SnowFlakeIdGenerator(1, 1);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    long id = generator.nextId();
                    System.out.println(id);
                }
            });
        }
        executorService.shutdown();
    }
}
