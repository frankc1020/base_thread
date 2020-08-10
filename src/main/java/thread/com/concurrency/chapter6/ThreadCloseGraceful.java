package thread.com.concurrency.chapter6;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @title: ThreadCloseGraceful
 * @projectName base_java
 * @description: TODO
 * @date 2020/8/4 16:57
 * 停止线程的方式--使用标志判断停止
 */
public class ThreadCloseGraceful {
    private static class Work implements Runnable{
        public static volatile  boolean start = true;
        @Override
        public void run() {
            while(start){

            }
        }

        public void shutDown(){
            start = false;
        }
    }

    public static void main(String[] args) {
        Work work = new Work();
        new Thread(work).start();

        //暂停一会线程
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        work.shutDown();
    }
}
