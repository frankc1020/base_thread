package zxx.threadpool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition实现两个线程交替执行
 */
public class ConditionCommuncation {
    public static void main(String[] args) {
        Business business = new Business();
        new Thread(
             new Runnable(){
                 @Override
                 public void run() {
                    for(int j=1;j<=50;j++){
                        business.sub(j);
                    }
                 }
             }).start();

        for(int i = 1;i<=50;i++){
            business.main(i);
        }
    }

    static class Business{
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        private boolean bShouldSub = true;
        public  void sub(int i){
            lock.lock();
            try{
                while(!bShouldSub){
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j=1;j<=10;j++){
                    System.out.println("sub Thread sequece of " + j + ",loop of " + i);
                }
                bShouldSub = false;
                condition.signal();
            }finally {
                lock.unlock();
            }
        }

        public void main(int i){
            lock.lock();
            try {
                while (bShouldSub) {
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 10; j++) {
                    System.out.println("main thread sequece of " + j + ",loop of " + i);
                }
                bShouldSub = true;
                condition.signal();

            }finally {
                lock.unlock();
            }
        }
    }
}
