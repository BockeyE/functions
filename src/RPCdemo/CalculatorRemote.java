package RPCdemo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class CalculatorRemote {
    public static final int PORT = 9090;

    public int add(int a, int b) throws Exception {
        // List<String> addressList =
        String addr = "localhost";

        Socket socket = new Socket(addr, PORT);

        CalculatorPRCRequest req = generateRequest(a, b);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeObject(req);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        Object o = ois.readObject();

        return (int) o;


    }

    private CalculatorPRCRequest generateRequest(int a, int b) {
        CalculatorPRCRequest calculatorPRCRequest = new CalculatorPRCRequest();
        calculatorPRCRequest.setA(a).setB(b).setMethod("add");
        return calculatorPRCRequest;
    }
}
