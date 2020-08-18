package thread.com.concurrency.chapter7;

/**
 * @author admin
 * @title: BankRunnable
 * @projectName base_java
 * @description: TODO
 * @date 2020/7/26 16:47
 */
public class BankVersion3 {

    public static void main(String[] args) {
        SynchronizedRunnable windowRunnable = new SynchronizedRunnable();
        Thread w1 = new Thread(windowRunnable,"一号柜台");
        Thread w2 = new Thread(windowRunnable,"二号柜台");
        Thread w3 = new Thread(windowRunnable,"三号柜台");

        w1.start();
        w2.start();
        w3.start();
    }
}
