package RSAdemo.implement.version1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author bockey
 * 主要用于计算超大整数超大次幂然后对超大的整数取模。
 * 这个算法叫做"蒙哥马利算法"。
 */

public class Exponentiation {
    /**
     * 超大整数超大次幂然后对超大的整数取模
     * (base ^ exponent) mod n
     */
    public BigInteger expMode(BigInteger base, BigInteger exponent, BigInteger n) {

        //ex 的 tostring 是把调用了biginteger的方法，把该大数字变成对应基数的字符串；
        //用builder是为了方便reverse
        char[] binarArray = new StringBuilder(exponent.toString(2)).reverse().toString().toCharArray();
        int r = binarArray.length;
        List<BigInteger> baseArray = new ArrayList<>();

        BigInteger preBase = base;
        baseArray.add(preBase);
        for (int i = 0; i < r - 1; i++) {
            BigInteger nexBase = preBase.multiply(preBase).mod(n);
            baseArray.add(nexBase);
            preBase = nexBase;
        }
        BigInteger a_w_b = this.multi
                (baseArray.toArray(new BigInteger[baseArray.size()]), binarArray);
        return a_w_b.mod(n);
    }

    public BigInteger multi(BigInteger[] array, char[] bin_array) {

        BigInteger result = BigInteger.ONE;
        for (int i = 0; i < array.length; i++) {
            BigInteger a = array[i];
            if (bin_array[i] == '0') {
                continue;
            }
            result = result.multiply(a);
        }
        return result;
    }

}




