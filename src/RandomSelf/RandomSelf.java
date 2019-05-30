package RandomSelf;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author bockey
 */
public class RandomSelf {

    private final static long C = 1;
    private final static long A = 48271;
    private final static long M = (1l << 31) - 1;

    private final AtomicLong seed = new AtomicLong();

    public RandomSelf() {
        this.seed.set(System.currentTimeMillis());
    }

    public RandomSelf(int seed) {

        this.seed.set(seed);
    }


    public long nextLong() {
        return (A * System.nanoTime() + C) % M;
    }

    public long nextInt(int num) {
        long r = (A * System.nanoTime() + C) % num;
        return Long.valueOf(r).intValue();
    }

    /*
    古老的LCG(linear congruential generator)代表了最好最朴素的伪随机数产生器算法。主要原因是容易理解，容易实现，而且速度快。

    LCG 算法数学上基于公式：

    X(0)=seed;
    X(n+1) = (A * X(n) + C) % M;

    其中，各系数为：

    X(0)表示种子seed

    模M, M > 0

    系数A, 0 < A < M

    增量C, 0 <= C < M

    原始值(种子) 0 <= X(0) < M

    其中参数c, m, a比较敏感，或者说直接影响了伪随机数产生的质量。

    一般来说我们采用M=(2^31)-1 = 2147483647，这个是一个31位的质数，A=48271，这个A能使M得到一个完全周期，这里C为奇数，同时如果数据选择不好的话，很有可能得到周期很短的随机数，例如，如果我们去Seed=179424105的话，那么随机数的周期为1，也就失去了随机的意义。

    (48271*179424105+1)mod(2的31次方-1)=179424105
     */
}
