package thread.com.concurrency.chapter4;

/**
 * @author admin
 * @title: DaemonThread
 * @projectName base_java
 * @description:守护线程 DaemonThread
 * @date 2020/7/30 17:09
 */
public class DaemonThread {

    public static void main(String[] args) {
        Thread t = new Thread(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "running....");
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+ "done....");
            }
        };//new

        t.start();
        t.setDaemon(true);
        //runnable-->running-dead-->blocked
//        t.start();


        System.out.println(Thread.currentThread().getName());

    }
}
