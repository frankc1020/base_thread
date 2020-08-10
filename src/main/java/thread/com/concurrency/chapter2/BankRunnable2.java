package thread.com.concurrency.chapter2;

/**
 * @author admin
 * @title: BankRunnable
 * @projectName base_java
 * @description: 使用lambda表达式
 * @date 2020/7/26 16:47
 */
public class BankRunnable2 {

    private final static int MAX = 50;
    private  static int index = 1;
    public static void main(String[] args) {

        final Runnable windowRunnable = ()->{

            while(index <= MAX){
                System.out.println("柜台："+ Thread.currentThread().getName() +"当前的号码是："+ index++);
                //一旦暂停一会，线程的不安全性就暴露出来了
                //暂停一会线程
//                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

            }
        };
        Thread w1 = new Thread(windowRunnable,"一号柜台");
        Thread w2 = new Thread(windowRunnable,"二号柜台");
        Thread w3 = new Thread(windowRunnable,"三号柜台");

        w1.start();
        w2.start();
        w3.start();
    }
}
