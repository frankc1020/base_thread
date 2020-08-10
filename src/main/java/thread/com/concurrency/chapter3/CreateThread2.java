package thread.com.concurrency.chapter3;

/**
 * @author admin
 * @title: CreateThread2
 * @projectName base_java
 * @description:
 * @date 2020/7/28 09:28
 *
 * 查看默认线程组是
 */
public class CreateThread2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

        System.out.println(threadGroup.activeCount());

        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (Thread temp : threads){
            System.out.println(temp);
        }

    }
}
