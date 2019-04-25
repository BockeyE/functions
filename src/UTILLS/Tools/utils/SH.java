package UTILLS.Tools.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 脚本同步执行类
 * 
 * @author PanJun
 * 
 */
public class SH {

    public static List<String> exec(String... command) throws InterruptedException, IOException {
        List<String> logList = new ArrayList<String>();
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
        BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));

        AtomicInteger counter = new AtomicInteger(0);
        ReaderThread stdErrThread = new ReaderThread(stdErr, logList, counter);
        ReaderThread stdOutThread = new ReaderThread(stdOut, logList, counter);
        stdErrThread.start();
        stdOutThread.start();
        process.waitFor();

        while (true) {
            synchronized (counter) {
                if (counter.get() < 2) {
                    counter.wait();
                } else {
                    break;
                }
            }
        }
        return logList;
    }

    public static List<String> exec(List<String> command) throws InterruptedException, IOException {
        String[] cmdArr = new String[command.size()];
        for (int i = 0; i < cmdArr.length; i++) {
            cmdArr[i] = command.get(i);
        }
        return exec(cmdArr);
    }

    public static List<String> tryExec(String... command) {
        try {
            return exec(command);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public static List<String> tryExec(List<String> command) {
        try {
            return exec(command);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}

class ReaderThread extends Thread {

    private final List<String> outputList;

    private final BufferedReader reader;

    private AtomicInteger counter;

    public ReaderThread(BufferedReader reader, List<String> outputList, AtomicInteger counter) {
        this.reader = reader;
        this.outputList = outputList;
        this.counter = counter;
        setDaemon(true);
    }

    public void run() {
        try {
            for (String currLine; (currLine = this.reader.readLine()) != null;) {
                synchronized (outputList) {
                    outputList.add(currLine);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            synchronized (counter) {
                counter.addAndGet(1);
                counter.notify();
            }
        }
    }
}