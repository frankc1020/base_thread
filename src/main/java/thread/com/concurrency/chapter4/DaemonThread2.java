package thread.com.concurrency.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @title: DaemonThread2
 * @projectName base_java
 * @description: 守护线程讲解
 * @date 2020/7/30 17:36
 * //第一个问题：在一个线程T1里面在创建一个新的线程T2，
 *    当把T2设置为守护线程的时候，那么T2线程会随着T1线程结束而结束
 *    当T2线程没有设置为守护线程的时候，那么T2线程不会随着T1线程的结束而结束。
 *
 *    setDaemon()
 *    将该线程标记为守护线程或用户线程。当正在运行的线程都是守护线程时，Java 虚拟机退出。
 *    ######该方法必须在启动线程前调用
 *
 *    如果在线程启动之后，设置该线程为守护线程，那么将会抛出异常java.lang.IllegalThreadStateException
 */
public class DaemonThread2 {

    public static void main(String[] args) {
        Thread t = new Thread(){
            @Override
            public void run() {
                Thread innerThread = new Thread(() -> {
                    while(true){
                        System.out.println("Do Something for health check");
                        //暂停一会线程
                        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                    }

                });

//                innerThread.setDaemon(true);
                innerThread.start();
                //暂停一会线程
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

                System.out.println("T Thread finish done");
            }
        };
        t.start();
    }

}

