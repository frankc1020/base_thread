package thread.com.concurrency.chapter6;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @title: ThreadCloseGraceful
 * @projectName base_java
 * @description: TODO
 * @date 2020/8/4 16:57
 * 停止线程的方式---使用interrupt 打断方法
 */
public class ThreadCloseGraceful2 {
    private static class Work extends Thread{
        @Override
        public void run() {
           while(true){
               if(Thread.interrupted())
                   break;
           }

        }

    }

    public static void main(String[] args) {
        Work work = new Work();
        work.start();

        //暂停一会线程
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        work.interrupt();
    }
}
