package zxx.threadpool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 3个线程使用Condition 指定首先执行main方法
 * 然后执行sub2，接着执行sub3，然后在执行main方法，循环执行50次
 */
public class ThreeConditionCommuncation {
    public static void main(String[] args) {
        Business business = new Business();
        new Thread(
             new Runnable(){
                 @Override
                 public void run() {
                    for(int j=1;j<=50;j++){
                        business.sub2(j);
                    }
                 }
             }).start();
        new Thread(
                new Runnable(){
                    @Override
                    public void run() {
                        for(int j=1;j<=50;j++){
                            business.sub3(j);
                        }
                    }
                }).start();


        for(int i = 1;i<=50;i++){
            business.main(i);
        }
    }

    static class Business{
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        private int bShouldSub = 1;
        public  void sub2(int i){
            lock.lock();
            try{
                while(bShouldSub != 2){
                    try {
                        condition2.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j=1;j<=10;j++){
                    System.out.println("sub2 Thread sequece of " + j + ",loop of " + i);
                }
                bShouldSub = 3;
                condition3.signal();
            }finally {
                lock.unlock();
            }
        }

        public  void sub3(int i){
            lock.lock();
            try{
                while( bShouldSub != 3){
                    try {
                        condition3.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j=1;j<=20;j++){
                    System.out.println("sub3 Thread sequece of " + j + ",loop of " + i);
                }
                bShouldSub = 1;
                condition1.signal();
            }finally {
                lock.unlock();
            }
        }

        public void main(int i){
            lock.lock();
            try {
                while (bShouldSub != 1) {
                    try {
                        condition1.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 10; j++) {
                    System.out.println("main thread sequece of " + j + ",loop of " + i);
                }
                bShouldSub = 2;
                condition2.signal();

            }finally {
                lock.unlock();
            }
        }
    }
}
