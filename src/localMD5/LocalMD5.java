package localMD5;

import java.security.MessageDigest;
import java.util.Arrays;

public class LocalMD5 {


    //标准的幻数
    private static final int A = 0x67452301;
    private static final int B = 0xefcdab89;
    private static final int C = 0x98badcfe;
    private static final int D = 0x10325476;

    private static final int S11 = 7;
    private static final int S12 = 12;
    private static final int S13 = 17;
    private static final int S14 = 22;

    private static final int S21 = 5;
    private static final int S22 = 9;
    private static final int S23 = 14;
    private static final int S24 = 20;

    private static final int S31 = 4;
    private static final int S32 = 11;
    private static final int S33 = 16;
    private static final int S34 = 23;

    private static final int S41 = 6;
    private static final int S42 = 10;
    private static final int S43 = 15;
    private static final int S44 = 21;

    private static final int GROUP_LEN = 64;

    public byte[] digest(byte[] input) {
        byte[] paddingData = getPaddingData(input); //对原始数据进行补位
        return process(paddingData);//处理分组，核心算法
    }

    private int[] getGroupData(byte[] data, int index) {
        int[] groupData = new int[16];
        for (int i = 0; i < 16; i++) {
            groupData[i] = (data[4 * i + index] & 0xFF) | //这里注意，在byte转int时一定要进行&0xff操作
                    (data[4 * i + 1 + index] & 0xFF) << 8 |
                    (data[4 * i + 2 + index] & 0xFF) << 16 |
                    (data[4 * i + 3 + index] & 0xFF) << 24;
        }
        return groupData;
    }

    private int[] getConstTable() {
        int[] T = new int[64];
        for (int i = 1; i < 65; i++) {
            T[i - 1] = (int) ((long) (Math.abs(Math.sin(i)) * 4294967296L));
        }
        return T;
    }


    private byte[] getPaddingData(byte[] input) {
        int length = input.length;
        long bitLength = length << 3;
        int rest = length % 64;
        int paddingLength = 0;
        if (rest < 56) {
            paddingLength = 64 - rest;
        } else {
            paddingLength = 128 - rest;
        }
        byte[] paddingData = new byte[length + paddingLength];
        for (int i = 0; i < length; i++) {
            paddingData[i] = input[i];
        }
        paddingData[length] = (byte) (1 << 7);
        for (int i = 1; i < paddingLength - 8; i++) {
            paddingData[length + i] = 0;
        }
        for (int i = 0; i < 8; i++) {
            paddingData[length + paddingLength - 8 + i] = (byte) (bitLength & 0xFF);
            bitLength = bitLength >>> 8;
        }
        return paddingData;
    }

    private byte[] process(byte[] data) {
        int[] result = {A, B, C, D};
        int length = data.length;
        int groupCount = length / 64; //计算分组数量,每组512位（64字节）
        int[] T = getConstTable();
        for (int groupIndex = 0; groupIndex < groupCount; groupIndex++) {
            int[] x = getGroupData(data, groupIndex * GROUP_LEN);//获取分组数据
            int a = result[0];
            int b = result[1];
            int c = result[2];
            int d = result[3];

            /*第一轮*/
            a = FF(a, b, c, d, x[0], S11, T[0]); //a = b + ((a + F(b,c,d) + X[0] + T[0]) <<< 7)
            d = FF(d, a, b, c, x[1], S12, T[1]); //d = a + ((d + F(a,b,c) + X[1] + T[1]) <<< 12)
            c = FF(c, d, a, b, x[2], S13, T[2]); //c = d + ((c + F(d,a,b) + X[2] + T[2]) <<< 17)
            b = FF(b, c, d, a, x[3], S14, T[3]); //b = c + ((b + F(c,d,a) + X[3] + T[3]) <<< 22)
            a = FF(a, b, c, d, x[4], S11, T[4]); //a = b + ((a + F(b,c,d) + X[4] + T[4]) <<< 7)
            d = FF(d, a, b, c, x[5], S12, T[5]); //d = a + ((d + F(a,b,c) + X[5] + T[5]) <<< 12)
            c = FF(c, d, a, b, x[6], S13, T[6]); //c = d + ((c + F(d,a,b) + X[6] + T[6]) <<< 17)
            b = FF(b, c, d, a, x[7], S14, T[7]); //b = c + ((b + F(c,d,a) + X[7] + T[7]) <<< 22)
            a = FF(a, b, c, d, x[8], S11, T[8]); //a = b + ((a + F(b,c,d) + X[8] + T[8]) <<< 7)
            d = FF(d, a, b, c, x[9], S12, T[9]); //d = a + ((d + F(a,b,c) + X[9] + T[9]) <<< 12)
            c = FF(c, d, a, b, x[10], S13, T[10]); //c = d + ((c + F(d,a,b) + X[10] + T[10]) <<< 17)
            b = FF(b, c, d, a, x[11], S14, T[11]); //b = c + ((b + F(c,d,a) + X[11] + T[12]) <<< 22)
            a = FF(a, b, c, d, x[12], S11, T[12]); //a = b + ((a + F(b,c,d) + X[12] + T[12]) <<< 7)
            d = FF(d, a, b, c, x[13], S12, T[13]); //d = a + ((d + F(a,b,c) + X[13] + T[13]) <<< 12)
            c = FF(c, d, a, b, x[14], S13, T[14]); //c = d + ((c + F(d,a,b) + X[14] + T[14]) <<< 17)
            b = FF(b, c, d, a, x[15], S14, T[15]); //b = c + ((b + F(c,d,a) + X[15] + T[15]) <<< 22)

            /*第二轮*/
            a = GG(a, b, c, d, x[1], S21, T[16]); //a = b + ((a + G(b,c,d) + X[1] + T[16]) <<< 5)
            d = GG(d, a, b, c, x[6], S22, T[17]); //d = a + ((d + G(a,b,c) + X[6] + T[17]) <<< 9)
            c = GG(c, d, a, b, x[11], S23, T[18]); //c = d + ((c + G(d,a,b) + X[11] + T[18]) <<< 14)
            b = GG(b, c, d, a, x[0], S24, T[19]); //b = c + ((b + G(c,d,a) + X[0] + T[19]) <<< 20)
            a = GG(a, b, c, d, x[5], S21, T[20]); //a = b + ((a + G(b,c,d) + X[5] + T[20]) <<< 5)
            d = GG(d, a, b, c, x[10], S22, T[21]); ///d = a + ((d + G(a,b,c) + X[10] + T[21]) <<< 9)
            c = GG(c, d, a, b, x[15], S23, T[22]); ///c = d + ((c + G(d,a,b) + X[15] + T[22]) <<< 14)
            b = GG(b, c, d, a, x[4], S24, T[23]); //b = c + ((b + G(c,d,a) + X[4] + T[23]) <<< 20)
            a = GG(a, b, c, d, x[9], S21, T[24]); ///a = b + ((a + G(b,c,d) + X[9] + T[24]) <<< 5)
            d = GG(d, a, b, c, x[14], S22, T[25]); //d = a + ((d + G(a,b,c) + X[14] + T[25]) <<< 9)
            c = GG(c, d, a, b, x[3], S23, T[26]); //c = d + ((c + G(d,a,b) + X[3] + T[26]) <<< 14)
            b = GG(b, c, d, a, x[8], S24, T[27]); //b = c + ((b + G(c,d,a) + X[8] + T[27]) <<< 20)
            a = GG(a, b, c, d, x[13], S21, T[28]); //a = b + ((a + G(b,c,d) + X[13] + T[28]) <<< 5)
            d = GG(d, a, b, c, x[2], S22, T[29]); //d = a + ((d + G(a,b,c) + X[2] + T[29]) <<< 9)
            c = GG(c, d, a, b, x[7], S23, T[30]); //c = d + ((c + G(d,a,b) + X[7] + T[30]) <<< 14)
            b = GG(b, c, d, a, x[12], S24, T[31]); //b = c + ((b + G(c,d,a) + X[12] + T[31]) <<< 20)

            /*第三轮*/
            a = HH(a, b, c, d, x[5], S31, T[32]); //a = b + ((a + H(b,c,d) + X[5] + T[32])<<< 4)
            d = HH(d, a, b, c, x[8], S32, T[33]); //d = a + ((d + H(a,b,c) + X[8] + T[33])<<< 11)
            c = HH(c, d, a, b, x[11], S33, T[34]); //c = d + ((c + H(d,a,b) + X[11] + T[34])<<< 16)
            b = HH(b, c, d, a, x[14], S34, T[35]); //b = c + ((b + H(c,d,a) + X[14] + T[35])<<< 23)
            a = HH(a, b, c, d, x[1], S31, T[36]); //a = b + ((a + H(b,c,d) + X[1] + T[36])<<< 4)
            d = HH(d, a, b, c, x[4], S32, T[37]); //d = a + ((d + H(a,b,c) + X[4] + T[37])<<< 11)
            c = HH(c, d, a, b, x[7], S33, T[38]); //c = d + ((c + H(d,a,b) + X[7] + T[38])<<< 16)
            b = HH(b, c, d, a, x[10], S34, T[39]); //b = c + ((b + H(c,d,a) + X[10] + T[39])<<< 23)
            a = HH(a, b, c, d, x[13], S31, T[40]); //a = b + ((a + H(b,c,d) + X[13] + T[40])<<< 4)
            d = HH(d, a, b, c, x[0], S32, T[41]); //d = a + ((d + H(a,b,c) + X[0] + T[41])<<< 11)
            c = HH(c, d, a, b, x[3], S33, T[42]); //c = d + ((c + H(d,a,b) + X[3] + T[42])<<< 16)
            b = HH(b, c, d, a, x[6], S34, T[43]); //b = c + ((b + H(c,d,a) + X[6] + T[43])<<< 23)
            a = HH(a, b, c, d, x[9], S31, T[44]); //a = b + ((a + H(b,c,d) + X[9] + T[44])<<< 4)
            d = HH(d, a, b, c, x[12], S32, T[45]); //d = a + ((d + H(a,b,c) + X[12] + T[45])<<< 11)
            c = HH(c, d, a, b, x[15], S33, T[46]); //c = d + ((c + H(d,a,b) + X[15] + T[46])<<< 16)
            b = HH(b, c, d, a, x[2], S34, T[47]); //b = c + ((b + H(c,d,a) + X[2] + T[47])<<< 23)

            /*第四轮*/
            a = II(a, b, c, d, x[0], S41, T[48]); //a = b + ((a + I(b,c,d) + X[0] + T[48]) <<< 6)
            d = II(d, a, b, c, x[7], S42, T[49]); //d = a + ((d + I(a,b,c) + X[7] + T[49]) <<< 10)
            c = II(c, d, a, b, x[14], S43, T[50]); //c = d + ((c + I(d,a,b) + X[14] + T[50]) <<< 15)
            b = II(b, c, d, a, x[5], S44, T[51]); //b = c + ((b + I(c,d,a) + X[5] + T[51]) <<< 21)
            a = II(a, b, c, d, x[12], S41, T[52]); //a = b + ((a + I(b,c,d) + X[12] + T[52]) <<< 6)
            d = II(d, a, b, c, x[3], S42, T[53]); //d = a + ((d + I(a,b,c) + X[3] + T[53]) <<< 10)
            c = II(c, d, a, b, x[10], S43, T[54]); //c = d + ((c + I(d,a,b) + X[10] + T[54]) <<< 15)
            b = II(b, c, d, a, x[1], S44, T[55]); //b = c + ((b + I(c,d,a) + X[1] + T[55]) <<< 21)
            a = II(a, b, c, d, x[8], S41, T[56]); //a = b + ((a + I(b,c,d) + X[8] + T[56]) <<< 6)
            d = II(d, a, b, c, x[15], S42, T[57]); //d = a + ((d + I(a,b,c) + X[15] + T[57]) <<< 10)
            c = II(c, d, a, b, x[6], S43, T[58]); //c = d + ((c + I(d,a,b) + X[6] + T[58]) <<< 15)
            b = II(b, c, d, a, x[13], S44, T[59]); //b = c + ((b + I(c,d,a) + X[13] + T[59]) <<< 21)
            a = II(a, b, c, d, x[4], S41, T[60]); //a = b + ((a + I(b,c,d) + X[4] + T[60]) <<< 6)
            d = II(d, a, b, c, x[11], S42, T[61]); //d = a + ((d + I(a,b,c) + X[11] + T[61]) <<< 10)
            c = II(c, d, a, b, x[2], S43, T[62]); //c = d + ((c + I(d,a,b) + X[2] + T[62]) <<< 15)
            b = II(b, c, d, a, x[9], S44, T[63]); //b = c + ((b + I(c,d,a) + X[9] + T[63]) <<< 21)

            result[0] += a;
            result[1] += b;
            result[2] += c;
            result[3] += d;
        }
        byte[] resultByte = new byte[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                resultByte[i * 4 + j] = (byte) (result[i] & 0xff);
                result[i] = result[i] >> 8;
            }
        }
        return resultByte;
    }


    private static int F(int x, int y, int z) {
        return (x & y) | ((~x) & z);
    }

    private static int G(int x, int y, int z) {
        return (x & z) | (y & (~z));
    }

    private static int H(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private static int I(int x, int y, int z) {
        return y ^ (x | (~z));
    }

    private static int FF(int a, int b, int c, int d, int x, int s, int t) {
        a += (F(b, c, d) & 0xFFFFFFFF) + x + t;
        a = ((a & 0xFFFFFFFF) << s) | ((a & 0xFFFFFFFF) >>> (32 - s)); //循环位移
        a += b;
        return (a & 0xFFFFFFFF);
    }

    private static int GG(int a, int b, int c, int d, int x, int s, int t) {
        a += (G(b, c, d) & 0xFFFFFFFF) + x + t;
        a = ((a & 0xFFFFFFFF) << s) | ((a & 0xFFFFFFFF) >>> (32 - s));
        a += b;
        return (a & 0xFFFFFFFF);
    }

    private static int HH(int a, int b, int c, int d, int x, int s, int t) {
        a += (H(b, c, d) & 0xFFFFFFFF) + x + t;
        a = ((a & 0xFFFFFFFF) << s) | ((a & 0xFFFFFFFF) >>> (32 - s));
        a += b;
        return (a & 0xFFFFFFFF);
    }

    private static int II(int a, int b, int c, int d, int x, int s, int t) {
        a += (I(b, c, d) & 0xFFFFFFFF) + x + t;
        a = ((a & 0xFFFFFFFF) << s) | ((a & 0xFFFFFFFF) >>> (32 - s));
        a += b;
        return (a & 0xFFFFFFFF);
    }

    public static void main(String[] args) {
        LocalMD5 myMd5 = new LocalMD5();
        String testData = "hello,world";
        System.out.println("--------My MD5--------");
        byte[] myResult = myMd5.digest(testData.getBytes());
        System.out.println(Arrays.toString(myResult));

        System.out.println("--------Java MD5--------");
        try {
            MessageDigest javaMd5 = MessageDigest.getInstance("MD5");
            byte[] javaResult = javaMd5.digest(testData.getBytes());
            System.out.println(Arrays.toString(javaResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**
 * 散列函数
 * 散列函数，也称作哈希函数，消息摘要函数，单向函数或者杂凑函数。散列函数主要用于验证数据的完整性。通过散列函数，
 * 可以创建消息的“数字指纹”，消息接收方可以通过校验消息的哈希值来验证消息的完整性，防止消息被篡改。散列函数具有以下特性：
 *
 * 散列函数的运算过程是不可逆的，这个称为散列函数的单向性。
 * 对于一个已知的消息及其散列值，要找到另外一个消息使其获得相同的散列值是不可能的，这个特性称为散列函数的弱碰撞性。这个特性可以用来防止消息伪造。
 * 任意两个不同消息的散列值一定不同。
 * 对原始消息长度没有限制。
 * 任何消息经过散列函数处理后，都会产生一个唯一的散列值，这个散列值可以用来验证消息的完整性。计算消息散列值的过程被称为“消息摘要”，
 * 计算消息散列值的算法被称为消息摘要算法。常使用的消息摘要算法有：MD—消息摘要算法，SHA—安全散列算法，MAC—消息认证码算法。本文主要来了解MD算法。
 *
 * MD5算法
 * MD5算法是典型的消息摘要算法，它是由MD4，MD3和MD2算法演变而来。无论是哪一种MD算法，
 * 其原理都是接受一个任意长度的消息并产生一个128位的消息摘要。如果把得到的消息摘要转换成十六进制字符串，
 * 则会得到一个32字节长度的字符串，我们平常见到的大部分MD数字指纹就是一个长度为32的十六进制字符串。
 *
 * MD5算法原理
 * 假设原始消息长度是b（以bit为单位），注意这里b可以是任意长度，并不一定要是8的整数倍。计算该消息MD5值的过程如下：
 *
 * 1.填充信息
 * 在计算消息的MD5值之前，首先对原始信息进行填充，这里的信息填充分为两步。
 * 第一步，对原始信息进行填充，填充之后，要求信息的长度对512取余等于448。填充的规则如下：假设原始信息长度为b bit，
 * 那么在信息的b+1 bit位填充1，剩余的位填充0，直到信息长度对512取余为448。这里有一点需要注意，如果原始信息长度对512取余正好等于448，
 * 这种情况仍然要进行填充，很明显，在这时我们要填充的信息长度是512位，直到信息长度对512取余再次等于448。所以，填充的位数最少为1，最大为512。
 *
 * 第二步，填充信息长度，我们需要把原始信息长度转换成以bit为单位，然后在第一步操作的结果后面填充64bit的数据表示原始信息长度。
 * 第一步对原始信息进行填充之后，信息长度对512取余结果为448，这里再填充64bit的长度信息，整个信息恰好可以被512整除。
 * 其实从后续过程可以看到，计算MD5时，是将信息分为若干个分组进行处理的，每个信息分组的长度是512bit。
 *
 * 2.信息处理
 * 在进行MD5值计算之前，我们先来做一些定义。
 *
 * 信息分组定义
 * 原始信息经过填充之后，最终得到的信息长度（bit）是512的整数倍，我们先对信息进行分组，每512bit为一个分组，
 * 然后再将每个信息分组（512bit）再细分为16个小的分组，每个小分组的长度为32bit。规定如下
 * Mp 代表经过填充之后的信息
 * LM 表示Mp的长度（以bit为单位）
 * N 表示分组个数，N = LM/512
 * M[i] 表示将原始信息进行分组后的第i个信息分组，其中i=1...N
 * X[i] 表示将M[i]进行分组后的第i个小分组，其中i=1...16
 * 标准幻数定义
 * 现定义四个标准幻数如下，
 * A = 01 23 45 67
 * B = 89 ab cd ef
 * C = fe dc ba 98
 * D = 76 54 32 10
 * 在计算机中存储时，采用小端存储方式，以A为例，A在Java中初始化的代码为为A=0x67452301
 * 常量表T
 * T是一个常量表，T[i] = 4294967296 * abs(sin(i))的运算结果取整，其中i=1...64
 * 辅助方法
 * 我们定义四个辅助方法。
 * F(x,y,z) = (x & y) | ((~x) & z)
 * G(x,y,z) = (x & z) | (y & (~z))
 * H(x,y,z) = x ^ y ^ z
 * I(x,y,z) = y ^ (x | (~z))
 * 其中，x，y，z长度为32bit
 *
 *     A=0x67452301
 *     B=0xefcdab89
 *     C=0x98badcfe
 *     D=0x10325476
 *     for( j=1;j<=N;j++){  //依次处理每个分组，其中N代表分组个数
 *       AA = A
 *       BB = B
 *       CC = C
 *       DD = D
 *       //开始处理分组，每个信息分组要经过4轮处理
 *       /*第一轮
 *       假设 [abcd k s i] 表示执行的运算是 a = b + ((a + F(b,c,d) + X[k] + T[i]) <<< s)，
 *       其中<<<表示循环移位。第一轮运算就是对分组执行以下所示的16次运算，运算的顺序从左到右。
 *[ABCD 0 7 1][DABC 1 12 2][CDAB 2 17 3][BCDA 3 22 4]
 *[ABCD 4 7 5][DABC 5 12 6][CDAB 6 17 7][BCDA 7 22 8]
 *[ABCD 8 7 9][DABC 9 12 10][CDAB 10 17 11][BCDA 11 22 12]
 *[ABCD 12 7 13][DABC 13 12 14][CDAB 14 17 15][BCDA 15 22 16]
 *
 *     /*第二轮
 *     假设 [abcd k s i] 表示执行的运算是 a = b + ((a + G(b,c,d) + X[k] + T[i]) <<< s)，
 *     其中 <<<表示循环移位。第一轮运算就是对分组执行以下所示的16次运算，运算的顺 序从左到右。
 *[ABCD 1 5 17][DABC 6 9 18][CDAB 11 14 19][BCDA 0 20 20]
 *[ABCD 5 5 21][DABC 10 9 22][CDAB 15 14 23][BCDA 4 20 24]
 *[ABCD 9 5 25][DABC 14 9 26][CDAB 3 14 27][BCDA 8 20 28]
 *[ABCD 13 5 29][DABC 2 9 30][CDAB 7 14 31][BCDA 12 20 32]
 *
 *     /*第三轮
 *     假设 [abcd k s i] 表示执行的运算是 a = b + ((a + H(b,c,d) + X[k] + T[i]) <<< s)，
 *     其中<<<表示循环移位。第一轮运算就是对分组执行以下所示的16次运算，运算的顺 序从左到右。
 *[ABCD 5 4 33][DABC 8 11 34][CDAB 11 16 35][BCDA 14 23 36]
 *[ABCD 1 4 37][DABC 4 11 38][CDAB 7 16 39][BCDA 10 23 40]
 *[ABCD 13 4 41][DABC 0 11 42][CDAB 3 16 43][BCDA 6 23 44]
 *[ABCD 9 4 45][DABC 12 11 46][CDAB 15 16 47][BCDA 2 23 48]
 *
 *     /*第四轮
 *     假设 [abcd k s i] 表示执行的运算是 a = b + ((a + I(b,c,d) + X[k] + T[i]) <<< s)，
 *     其中<<<表示循环移位。第一轮运算就是对分组执行以下所示的16次运算，运算的顺序从左到右。
 *[ABCD 0 6 49][DABC 7 10 50][CDAB 14 15 51][BCDA 5 21 52]
 *[ABCD 12 6 53][DABC 3 10 54][CDAB 10 15 55][BCDA 1 21 56]
 *[ABCD 8 6 57][DABC 15 10 58][CDAB 6 15 59][BCDA 13 21 60]
 *[ABCD 4 6 61][DABC 11 10 62][CDAB 2 15 63][BCDA 9 21 64]
 *
 *     //将当前消息分组的运算结果和上一次的结果进行累加
 *A =A +AA
 *B =B +BB
 *C =C +CC
 *D =D +DD
 *
 * 最终我们按照低字节在前的顺序依次将A,B,C,D拼接起来，就是计算得到的MD5值，因此，MD5值的长度是固定的，为128bit。
 *
 *
 */
}
