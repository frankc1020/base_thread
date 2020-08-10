package zxx.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newFixedThreadPool(3);
       /* ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <=10 ; i++) {
            final int task = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 1; j <= 10; j++) {
                        System.out.println(Thread.currentThread().getName() + "loop of " + j + " for task of " + task);
                    }
                }
            });
        }*/

        //定时器  开始之后多长时间执行
     /*  Executors.newScheduledThreadPool(3).schedule(new Runnable() {
           @Override
           public void run() {
               System.out.println("bombing");
           }
       },3,TimeUnit.SECONDS);*/

        /**
         * 开始之后initialDelay时间执行，然后每隔period时间执行
         */
        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("bombing");
            }
        },4,2,TimeUnit.SECONDS);

    }
}
