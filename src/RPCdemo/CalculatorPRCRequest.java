package RPCdemo;


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
