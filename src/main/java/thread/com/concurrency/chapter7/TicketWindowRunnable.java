package thread.com.concurrency.chapter7;

/**
 * @author admin
 * @title: TicketWindowRunnable
 * @projectName base_java
 * @description: 实现Runnbale接口实现数据访问唯一
 * @date 2020/7/26 16:46
 */
public class TicketWindowRunnable implements Runnable {

    private  final static int MAX = 50;

    private  int index =1;

    private final  Object MONITOR = new Object();

    @Override
    public void run() {
        while(true){
            synchronized (MONITOR){
                if(index > MAX){
                    break;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("柜台："+ Thread.currentThread().getName() +"当前的号码是："+ index++);
            }

        }
    }
}
