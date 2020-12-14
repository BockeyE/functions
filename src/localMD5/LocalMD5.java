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

}
