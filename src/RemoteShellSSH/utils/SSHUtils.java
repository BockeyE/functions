package RemoteShellSSH.utils;

import com.jcraft.jsch.*;

import java.io.InputStream;

/**
 * @author bockey
 */
public class SSHUtils {
    public static final int DEFAULT_PORT = 22;
    private static final String FUNCTION_EXEC = "exec";
    private String host = "";
    private String user = "root";
    private String password = "123456";
    private Integer port = 22;
    private Shell shell;

    public static SSHUtils config(String host, String user, String password, int port) {
        SSHUtils ssh = new SSHUtils();
        ssh.host = host;
        ssh.user = user;
        ssh.password = password;
        ssh.port = port;
        ssh.shell = new Shell(host, port, user, password);
        return ssh;
    }

    public String execute(String... commands) {
        shell.executeCommands(commands);
        String response = shell.getResponse();
        shell.disconnect();
        return response;
    }

    public Session getSession() {
        return shell.getSession();
    }

    public String execute(String command) throws Exception {
        ChannelExec channel = this.getChannelExec(user, host, port);
        InputStream in = channel.getInputStream();
        channel.setCommand(command);
        channel.connect();

        StringBuilder sb = new StringBuilder();
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0)
                    break;
                sb.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0)
                    continue;
                break;
            }
        }

        channel.disconnect();
        return sb.toString();
    }

    public void executePrint(String command) throws Exception {
        ChannelExec channel = this.getChannelExec(user, host, port);
        InputStream in = channel.getInputStream();
        channel.setCommand(command);
        channel.connect();

        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0)
                    break;
                System.out.println(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0)
                    continue;
                break;
            }
        }

        channel.disconnect();
    }

    private ChannelExec getChannelExec(String user, String host, int port) throws Exception {
        return (ChannelExec) getChannel(user, host, port, FUNCTION_EXEC);
    }

    private Channel getChannel(String user, String host, int port, String functionStr) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        UserInfo ui = new MUserInfo(password);
        session.setUserInfo(ui);
        session.connect();
        return session.openChannel(functionStr);
    }

    public static class MUserInfo implements UserInfo {
        private String password;

        public MUserInfo(String password) {
            this.password = password;
        }

        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
            this.password = password;
        }

        public boolean promptYesNo(String str) {
            return true;
        }

        public String getPassphrase() {
            return null;
        }

        public boolean promptPassphrase(String message) {
            return true;
        }

        public boolean promptPassword(String message) {
            return true;
        }

        public void showMessage(String message) {

        }


        public String getPassword() {
            return this.password;
        }
    }
}
