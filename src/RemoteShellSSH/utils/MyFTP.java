package RemoteShellSSH.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.*;

/**
 * @author bockey
 */
public class MyFTP {


    public static void FTPUpFileByFilePath(Session session, String filePath, String targetPath) {
        FTPUpFileByFile(session, new File(filePath), targetPath);
    }


    public static void FTPUpFileByFile(Session session, File file, String targetPath) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FTPUpFileByInputStream(session, fis, file.getName(), targetPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void FTPUpFileByInputStream(Session session, InputStream ips, String filename, String targetPath) {
        Channel channel = null;
        try {
            channel = session.openChannel("sftp");
            channel.connect(10000000);
            ChannelSftp sftp = (ChannelSftp) channel;
            OutputStream outstream = null;
            try {
                sftp.cd(targetPath);
            } catch (SftpException e) {
                sftp.mkdir(targetPath);
                sftp.cd(targetPath);
            }
            try {
                sftp.cd(sftp.pwd());
            } catch (SftpException e1) {
                e1.printStackTrace();
                System.out.println("sftp cd error");
            }
            try {
                outstream = sftp.put(filename);
                byte buf[] = new byte[1024];
                int n = 0;
                try {
                    while ((n = ips.read(buf)) != -1) {
                        outstream.write(buf, 0, n);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("stream error");
                }
            } finally {
                try {
                    outstream.flush();
                    outstream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.disconnect();
        }
    }
}
