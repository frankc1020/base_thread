package thread.com.concurrency.chapter7;

/**
 * @author admin
 * @title: SynchronizedRunnable
 * @projectName base_thread
 * @description: 同步方法和同步代码块的区别
 * @date 2020/8/18 08:53
 */
public class SynchronizedRunnable implements Runnable {
    private  final static int MAX = 50;

    private  int index =1;

    private final  Object MONITOR = new Object();

    @Override
    public void run() {
        while(true){
            if(index > MAX){
                break;
            }
            ticket();
        }
    }

    public  boolean ticket(){
        if(index > MAX)
            return true;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("柜台："+ Thread.currentThread().getName() +"当前的号码是："+ index++);
        return false;
    }
}
