package thread.com.concurrency.chapter5;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author admin
 * @title: ThreadJoin
 * @projectName base_java
 * @description: join的用法
 * @date 2020/8/1 14:01
 *
 * join方法 分为两种：无参和有参
 * 无参：就是当线程使用无参的join方法之后，主线程会等待该线程结束之后才会执行
 * 有参：当线程使用有参数的join方法之后，例如：join(100),主线程会在等待100ms之后接着执行
 *
 */
public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() ->{
            IntStream.range(1,1000).forEach(i -> System.out.println(Thread.currentThread().getName() + "---->" + i));
        });
        Thread t2 = new Thread(() ->{
            IntStream.range(1,1000).forEach(i -> System.out.println(Thread.currentThread().getName() + "---->" + i));
        });

        Thread t3 = new Thread(() ->{
            System.out.println("t3 is running......");
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t3 is done.....");

        });

//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
        t3.start();
//        t3.join(100);//主线程在等待100ms之后接着执行
        t3.join(100,10);//主线程在等待100ms零10ns之后执行
        Optional.of("All of task finish done.").ifPresent(System.out::println);
        IntStream.range(1,1000).forEach(i -> System.out.println(Thread.currentThread().getName() + "---->" + i));


    }
}
