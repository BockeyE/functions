package RSAdemo.implement.version1;

import java.math.BigInteger;

/**
 * @author bockey
 */
public class GCD {
    //辗转相除法，递归求一对数字的最大公约数。
    //例子如下
    // 2146＝1813×1＋333
    //1813＝333×5＋148
    //333＝148×2＋37
    //148＝37×4＋0
    public BigInteger greatestCommonDivisor(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return a;
        } else {
            return greatestCommonDivisor(b, a.mod(b));
        }
    }

    public BigInteger[] extGCD(BigInteger a, BigInteger b) {
        // <p>扩展欧几里得算法：
        // <p>求ax + by = 1中的x与y的整数解（a，b互质）

        if (b.equals(BigInteger.ZERO)) {
            BigInteger x1 = BigInteger.ONE;
            BigInteger y1 = BigInteger.ZERO;
            BigInteger x = x1;
            BigInteger y = y1;
            BigInteger r = a;
            BigInteger[] result = {r, x, y};
            return result;
        } else {
            BigInteger[] tem = extGCD(b, a.mod(b));
            BigInteger r = tem[0];
            BigInteger x1 = tem[1];
            BigInteger y1 = tem[2];

            BigInteger x = y1;
            BigInteger y = x1.subtract(a.divide(b).multiply(y1));
            BigInteger[] result = {r, x, y};
            return result;
        }
    }
}
