package RSAdemo;

import java.util.ArrayList;
import java.util.List;


public class RSAdemo {

    //    核心pq，随机任选2个数字，越大越好
    private Integer q;
    private Integer p;


    private Integer e;
    private String X;
    private String Y;
    private Integer m;
    private Integer d;
    private Integer n;

    public RSAdemo(Integer q, Integer p, Integer e, Integer d) {
        this.q = q;
        this.p = p;
        this.e = e;
        this.d = d;
        n = p * q;
        m = (p - 1) * (q - 1);
        checkValid();
    }

    private void checkValid() {

        if (e != ((p - 1) * (q - 1))) {
            System.out.println(" e was not valid");
            System.out.println("please change e");
        }
        if ((d * e) % ((p - 1) * (q - 1)) != 1) {
            System.out.println(" d was not valid");
            System.out.println("please change d");
        }

    }

    public void showKeys() {
        System.out.println(
                " n : " + (q * p)
        );
    }

    public Object[] encodeByinfo(int[] x) {
        List arr = new ArrayList();
        for (int a : x) {
            arr.add(encoreCode(a));
        }
        return (arr.toArray());

    }

    private int encoreCode(int c) {
        int a = (int) Math.pow(c, e);
        a = a % n;
        return (char) a;
    }

    public Object[] decodeByinfo(Object[] x) {

        List arr = new ArrayList();
        for (Object a : x) {
            arr.add(decoreCode((Integer) a));
        }

        return (arr.toArray())
                ;
    }

    private int decoreCode(int c) {
        int a = (int) Math.pow(c, d);
        a = a % n;
        return a;
    }

//    n=pq
//    计算欧拉函数φ(n)=m
//    m=(p-1)(q-1)
//    e 是m互质且小于m的任一整数，1<e<m，e就是公钥
//    一般情况下选择一个质数即可，比如17。（实际应用中，常常选择65537。）

//    计算e对于m的模反元素d。
//    所谓"模反元素"就是指有一个整数d，可以使得ed被m除的余数为1。
//    ed ≡ 1 (mod m)
//    这个式子等价于
//    ed - 1 = k m
//    于是，找到模反元素d，实质上就是对下面这个二元一次方程求解。
//    ex + my = 1
//    例如，pq分别为61、53；n=3233；e=17；17x+3233y=1 ； =>
//    这个方程可以用"扩展欧几里得算法"求解，此处省略具体过程。总之，算出一组整数解为 (x,y)=(2753,-15)，即 d=2753。
//    n和e封装成公钥，n和d封装成私钥。
//    上述例子中，n=3233，e=17，d=2753，所以公钥就是 (3233,17)，私钥就是（3233, 2753）

//    假设要向持有人发送加密信息X，就要公钥 (n,e) 对X进行加密。这里需要注意，
//    X必须是整数（字符串可以取ascii值或unicode值），且X必须小于n。
//    所谓"加密"，就是算出下式的c：
//    X^e ≡ c (mod n)
//    公钥是 (3233, 17)，密文X假设是65，那么可以算出下面的等式：
//    65^17 ≡ 2790 (mod 3233)
//    于是，c等于2790，就把2790发出

//    解密要用私钥(n,d)
//    持有人拿到发来的2790后，就用私钥(3233, 2753) 进行解密
//    可以证明，下面的等式一定成立：
//    cd ≡ m (mod n)
//    也就是说，c的d次方除以n的余数为m。现在，c等于2790，私钥是(3233, 2753)，算出
//    2790^2753 ≡ 65 (mod 3233)
//    因此，加密前的原文就是65
//    至此，"加密--解密"的整个过程全部完成。
//    我们可以看到，如果不知道d，就没有办法从c求出m。
//    而前面已经说过，要知道d就必须分解n，这是极难做到的，所以RSA算法保证了通信安全。
//    你可能会问，公钥(n,e) 只能加密小于n的整数m，那么如果要加密大于n的整数，该怎么办？
//    有两种解决方法：一种是把长信息分割成若干段短消息，每段分别加密；
//    另一种是先选择一种"对称性加密算法"（比如DES），用这种算法的密钥加密信息，再用RSA公钥加密DES密钥。
}

//
//    私钥解密的证明
//    为什么用私钥解密，一定可以正确地得到m
//    也就是证明下面这个式子：
//    cd ≡ m (mod n)
//    根据加密规则
//    ｍe ≡ c (mod n)
//    于是，c可以写成下面的形式：
//    c = me - kn
//    将c代入要我们要证明的那个解密规则：
//    (me - kn)d ≡ m (mod n)
//    它等同于求证
//    med ≡ m (mod n)
//    由于
//    ed ≡ 1 (mod φ(n))
//    所以
//    ed = hφ(n)+1
//    将ed代入：
//    mhφ(n)+1 ≡ m (mod n)
//    接下来，分成两种情况证明上面这个式子。
//    1）m与n互质。
//    根据欧拉定理，此时
//    mφ(n) ≡ 1 (mod n)
//    得到
//    (mφ(n))h × m ≡ m (mod n)
//    原式得到证明。
//    2）m与n不是互质关系。
//    此时，由于n等于质数p和q的乘积，所以m必然等于kp或kq。
//    以 m = kp为例，考虑到这时k与q必然互质，则根据欧拉定理，下面的式子成立：
//    (kp)q-1 ≡ 1 (mod q)
//    进一步得到
//    [(kp)q-1]h(p-1) × kp ≡ kp (mod q)
//    即
//    (kp)ed ≡ kp (mod q)
//    将它改写成下面的等式
//    (kp)ed = tq + kp
//    这时t必然能被p整除，即 t=t'p
//    (kp)ed = t'pq + kp
//    因为 m=kp，n=pq，所以
//    med ≡ m (mod n)
//    原式得到证明。