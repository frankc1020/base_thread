package thread.com.concurrency.chapter5;

/**
 * @author admin
 * @title: ThreadJoin2
 * @projectName base_java
 * @description: TODO
 * @date 2020/8/1 14:24
 */
public class ThreadJoin2 {
    public static void main(String[] args) throws InterruptedException {
       long beginTime = System.currentTimeMillis();
        Thread t1 = new Thread(new CaptureRunnable("M1",5000L));
        Thread t2 = new Thread(new CaptureRunnable("M2",15000L));
        Thread t3 = new Thread(new CaptureRunnable("M3",10000L));

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

       long endTime = System.currentTimeMillis();
        System.out.printf("Save data begin timestamp is: %s, end timestamp is :%s, and used total %s\n",beginTime,endTime, (endTime-beginTime));

    }
}

class CaptureRunnable implements Runnable{
    private String machineName;

    private long spendTime;

    public CaptureRunnable(String machineName, long spendTime) {
        this.machineName = machineName;
        this.spendTime = spendTime;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(spendTime);
            System.out.println(machineName + " completed data capture and success. ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
