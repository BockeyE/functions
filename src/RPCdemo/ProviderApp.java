package RPCdemo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ProviderApp {

    private Calculator c = new CalculatorSub();


    public static void main(String[] args) throws Exception {
        new ProviderApp().run();
    }

    private void run() throws Exception {

        ServerSocket svs = new ServerSocket(9090);

        while (true) {
            Socket socket = svs.accept();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            Object o = ois.readObject();

            int result = 0;

            if (o instanceof CalculatorPRCRequest) {

                CalculatorPRCRequest ccp = (CalculatorPRCRequest) o;

                if ("add".endsWith(ccp.getMethod())) {
                    result = c.add(ccp.getA(), ccp.getB());
                }

            }

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(new Integer(result));

            socket.close();


        }

    }
}
