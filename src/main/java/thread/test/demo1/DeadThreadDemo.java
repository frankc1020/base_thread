package thread.test.demo1;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @title: DeadThreadDemo
 * @projectName base_thread
 * @description: TODO
 * @date 2021/2/5 12:20
 */
public class DeadThreadDemo {

    private static DeadThreadDemo resource1 = new DeadThreadDemo();
    private static DeadThreadDemo resource2 = new DeadThreadDemo();

    public static void main(String[] args) {
        new Thread(() ->{
            synchronized (resource1){
                System.out.println(Thread.currentThread().getName()+"获取资源resource1。。。。");
                //暂停一会线程
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (resource2){
                    System.out.println(Thread.currentThread().getName()+"获取资源resource2。。。。");
                }

            }
        },"线程A").start();

        new Thread(() ->{
            synchronized (resource2){
                System.out.println(Thread.currentThread().getName()+"获取资源resource2。。。。");
                //暂停一会线程
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (resource1){
                    System.out.println(Thread.currentThread().getName()+"获取资源resource1。。。。");
                }

            }
        },"线程B").start();
    }

}
