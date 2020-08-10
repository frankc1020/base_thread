package thread.com.concurrency.chapter6;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @title: ThreadCloseForce
 * @projectName base_java
 * @description: TODO
 * @date 2020/8/4 17:19
 */
public class ThreadCloseForce {
    public static void main(String[] args) {
        ThreadService service = new ThreadService();
        long start = System.currentTimeMillis();
        service.execute(()->{
            //load a very heavy resource
//            while(true){
//
//            }

            //暂停一会线程
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        });
        service.shutdown(10_000);
        long end = System.currentTimeMillis();
        System.out.println(end -start);
    }
}
