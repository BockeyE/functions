package RemoteShellSSH;

/**
 * @author bockey
 */
public class tre {


    public static void main(String[] args) throws Exception {
        SSHUtils shell = SSHUtils.config(" ", "root", "123456", 22);
        String execute = shell.execute("docker ps -a");
        System.out.println(execute);
    }
}
