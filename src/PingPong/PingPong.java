package PingPong;

/**
 * @author bockey
 */
public class PingPong extends Thread {

    String word; // what word to print
    int delay; // how long to pause

    PingPong(String whatToSay, int delayTime) {
        word = whatToSay;
        delay = delayTime;
    }

    public void run() {
        try {
            for (; ; ) {
                System.out.print(word + " ");
                sleep(delay); // wait until next time
            }
        } catch (InterruptedException e) {
            return; // end this thread
        }
    }

    public static void main(String[] args) {
        new PingPong("ping", 33).start(); // 1/30 second
        new PingPong("PONG", 100).start(); // 1/10 second}}[Listing Two]Account class Account {private double balance;public Account(double initialDeposit) {balance = initialDeposit;}public synchronized double getBalance() {return balance;}public synchronized void deposit(double amount) {balance += amount;}}[Listing Three]synchronized_abs /* make all elements in the array nonnegative /public static void abs(int[] values) {synchronized (values) {for (int i = 0; i < values.length; i++) {if (values[i] < 0)values[i] = -values[i];}}}[Listing Four]class Queue {// The first and last elements in the queueElement head, tail;public synchronized void append(Element p) {if (tail == null)head = p;elsetail.next = p;p.next = null;tail = p;notify(); // Let waiters know something arrived}public synchronized Element get() {try {while(head == null)wait(); // Wait for an element} catch (InterruptedException e) {return null;}Element p = head; // Remember first elementhead = head.next; // Remove it from the queueif (head == null) // Check for an empty queuetail = null;return p;}}[Listing Five]Thread spinner; // the thread doing the processingpublic void userHitCancel() {spinner.suspend(); // whoa!if (askYesNo("Really Cancel?"))spinner.stop(); // stop itelsespinner.resume(); // giddyap!}[Listing Six]class CalcThread extends Thread {private double Result;public void run() {Result = calculate();}public double result() {return Result;}public double calculate() {// ...}}class Join {public static void main(String[] args) {CalcThread calc = new CalcThread();calc.start();doSomethingElse();try {calc.join();System.out.println("result is "+ calc.result());} catch (InterruptedException e) {System.out.println("No answer: interrupted");}}}

//    PingPong

//    class PingPong extends Thread {
//        String word; // what word to printint delay; // how long to pausePingPong(String whatToSay, int delayTime) {word = whatToSay;delay = delayTime;}public void run() {try {for (;;) {System.out.print(word + " ");sleep(delay); // wait until next time}} catch (InterruptedException e) {return; // end this thread}}public static void main(String[] args) {new PingPong("ping", 33).start(); // 1/30 secondnew PingPong("PONG", 100).start(); // 1/10 second}}[Listing Two]Account class Account {private double balance;public Account(double initialDeposit) {balance = initialDeposit;}public synchronized double getBalance() {return balance;}public synchronized void deposit(double amount) {balance += amount;}}[Listing Three]synchronized_abs /* make all elements in the array nonnegative /public static void abs(int[] values) {synchronized (values) {for (int i = 0; i < values.length; i++) {if (values[i] < 0)values[i] = -values[i];}}}[Listing Four]class Queue {// The first and last elements in the queueElement head, tail;public synchronized void append(Element p) {if (tail == null)head = p;elsetail.next = p;p.next = null;tail = p;notify(); // Let waiters know something arrived}public synchronized Element get() {try {while(head == null)wait(); // Wait for an element} catch (InterruptedException e) {return null;}Element p = head; // Remember first elementhead = head.next; // Remove it from the queueif (head == null) // Check for an empty queuetail = null;return p;}}[Listing Five]Thread spinner; // the thread doing the processingpublic void userHitCancel() {spinner.suspend(); // whoa!if (askYesNo("Really Cancel?"))spinner.stop(); // stop itelsespinner.resume(); // giddyap!}[Listing Six]class CalcThread extends Thread {private double Result;public void run() {Result = calculate();}public double result() {return Result;}public double calculate() {// ...}}class Join {public static void main(String[] args) {CalcThread calc = new CalcThread();calc.start();doSomethingElse();try {calc.join();System.out.println("result is "+ calc.result());} catch (InterruptedException e) {System.out.println("No answer: interrupted");}}}
    }
}