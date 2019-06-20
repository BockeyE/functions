package RemoteShellSSH.utils;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.Hashtable;

/**
 * @author bockey
 */
public class MyShell {
    // ssh服务器的ip地址
    private String ip;
    // ssh服务器的登入端口
    private int port;
    // ssh服务器的登入用户名
    private String user;
    // ssh服务器的登入密码
    private String password;
    private Session session;
    private ChannelShell channel;
    private InputStream ips;
    private OutputStream ops;


    public MyShell(String ip, String user, String password, int port) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
        try {
            init();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    private void init() throws JSchException, IOException {
        JSch jsch = new JSch();
        session = jsch.getSession(user, ip, port);
        session.setPassword(password);
        Hashtable<String, String> config = new Hashtable<String, String>();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        Shell.localUserInfo ui = new Shell.localUserInfo();
        session.setUserInfo(ui);
        session.connect();
        channel = (ChannelShell) session.openChannel("shell");
        channel.connect();
        ips = channel.getInputStream();
        ops = channel.getOutputStream();
    }

    public void printShellByInputStream() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(ips));
        String msg = null;
        while ((msg = in.readLine()) != null) {
            System.out.println(msg);
        }
        in.close();
    }

    public void sendCommandWithExec(String cmd) throws IOException {
        ops.write(cmd.getBytes());
        ops.write("\n\r".getBytes());
        ops.flush();
    }

    public void disconnectAndCloseAll() {
        try {
            ips.close();
            ops.close();
            channel.disconnect();
            session.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
