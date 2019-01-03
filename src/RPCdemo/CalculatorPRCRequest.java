package RPCdemo;


//

/**
 * RPC,全称是 Remote Procedure Call， 即是远程过程调用，
 * 有一个计算功能的 Calculator，实现类impl，
 * 如果改成远程调用模式，从另一个主机调用本机的calculator类的方法内容
 * 如果是http的话，需要发起一个http request请求，
 * rpc可以实现 类似本地调用的调用方式不经过http
 * rpc 一般不涉及http协议，因为本质上http是用来传输文本的协议，rpc
 * 调用程序，完全可以使用二进制传输，比如利用java的 socket
 *
 */
public class CalculatorPRCRequest {

    private String method;
    private int a;
    private int b;

    public String getMethod() {
        return method;
    }

    public CalculatorPRCRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    public int getA() {
        return a;
    }

    public CalculatorPRCRequest setA(int a) {
        this.a = a;
        return this;
    }

    public int getB() {
        return b;
    }

    public CalculatorPRCRequest setB(int b) {
        this.b = b;
        return this;
    }
}
