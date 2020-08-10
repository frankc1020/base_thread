package thread.com.concurrency.chapter3;

/**
 * @author admin
 * @title: CreateThread
 * @projectName base_java
 * @description: TODO
 * @date 2020/7/30 12:33
 *
 * 线程构造函数
 */
public class CreateThread {

    public static void main(String[] args) {
        //无参构造
        Thread t1 = new Thread();
        //
        Thread t2 = new Thread(){
            @Override
            public void run() {
                System.out.println("=======t2=======");
            }
        };
        t1.start();
        t2.start();

        System.out.println(t1.getName());
        System.out.println(t2.getName());

        Thread t3 = new Thread("MyName");
        Thread t4 = new Thread(() -> {
            System.out.println("Runnable...." + Thread.currentThread().getName());
        });
        System.out.println(t3.getName());
        System.out.println(t4.getName());

        Thread t5 = new Thread(() -> {
            System.out.println("Runnable...." + Thread.currentThread().getName());
        },"RunnableThread");
        t5.start();

    }

}
