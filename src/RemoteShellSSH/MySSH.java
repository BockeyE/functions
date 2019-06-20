package RemoteShellSSH;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;

/**
 * @author bockey
 */
public class MySSH {
    public static void send(String host, String user, String password, int port) throws JSchException, IOException {
        SSHUtils config = SSHUtils.config(host, user, password, port);
        Session session = config.getSession();
        ChannelShell channel = (ChannelShell) session.openChannel("shell");
        channel.connect();
        int k = 1;
        InputStream ips = channel.getInputStream();
        OutputStream ops = channel.getOutputStream();
        String cmd = "cd /opt/gopath/src/github.com/hyperledger/fabric-samples/first-network \n\r";
        ops.write(cmd.getBytes());
        ops.write("\n\r".getBytes());
        ops.write("./byfn.sh -m up -c testchannel".getBytes());
        ops.write("\n\r".getBytes());
        ops.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(ips));
        String msg = null;
        while ((msg = in.readLine()) != null) {
            System.out.println(msg);
        }
        in.close();
    }
}
