package RemoteShellSSH.utils;

/**
 * @author bockey
 */
public class tre {


    public static void main(String[] args) throws Exception {
        SSHUtils shell = SSHUtils.config("192.168.3.116", "root", "123456", 22);
        String execute = shell.execute("docker ps -a");


        MyShell myShell = new MyShell("192.168.3.116", "root", "123456", 22);
        myShell.sendCommandWithExec("cd /home");
        myShell.sendCommandWithExec("ls");
        myShell.printShellByInputStream();


//        FTPUpFile.FTPUpFile(session, "C:\\Users\\user1\\Desktop\\test\\tem.txt", "/home/tem");
//        File f = new File("C:\\Users\\user1\\Desktop\\test\\tem.txt");
//        FileInputStream fis = new FileInputStream(f);
//        FTPUpFile.FTPUpFileByInputStream(session, fis, "cc.txt", "/home/tem");
//        fis.close();
//        String execute2 = shell.execute("echo `date`");
//        System.out.println(execute2);
    }
}
