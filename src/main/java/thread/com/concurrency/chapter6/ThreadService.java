package thread.com.concurrency.chapter6;

import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @title: ThreadService
 * @projectName base_java
 * @description: TODO
 * @date 2020/8/4 17:09
 */
public class ThreadService {

    private Thread executeThread;

    private boolean finished = false;
    
    public void execute(final Runnable task){
        executeThread = new Thread(){
            @Override
            public void run() {
                Thread runner = new Thread(task);
                runner.setDaemon(true);

                runner.start();

                try {
                    runner.join();
                    finished = true;
                }catch (InterruptedException e){
//                    e.printStackTrace();
                }
            }
        };

        executeThread.start();
    }

    public void shutdown(long mills){
        long current = System.currentTimeMillis();
        while(!finished){
            if(System.currentTimeMillis() - current >= mills){
                System.out.println("任务超时，需要结束他！！");
                executeThread.interrupt();
                break;
            }
            //暂停一会线程
            try { TimeUnit.MILLISECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        }
    }
}
