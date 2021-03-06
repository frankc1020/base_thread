package thread.test.demo1;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @title: DeadThreadDemo
 * @projectName base_thread
 * @description: TODO
 * @date 2021/2/5 12:20
 *
 *
 * 线程死锁：两个线程或多个线程，同时请求其他线程已经申请到资源，形成一个循环，即申请不到其它的资源
 * 也不释放自己已经申请到的资源，所以形成了线程死锁
 *
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
