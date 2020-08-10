package zxx.threadpool;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest2 {
    public static void main(String[] args) {
        final Queue qe = new Queue();
        for (int i = 0; i < 3; i++) {
            new Thread(){
                public void run() {
                    while (true) {
                        qe.get();
                    }
                }
            }.start();

            new Thread(){
                public void run() {
                    while(true){
                        qe.put(new Random().nextInt(1000));
                    }

                }
            }.start();
        }
    }
}

class Queue{
    private Object data = null;
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    public void get(){
        rwl.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "be ready read data!");
            Thread.sleep((long)(Math.random()*1000));
            System.out.println(Thread.currentThread().getName() + "have be read data " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();
        }
    }
    public void put(Object data){
        rwl.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "be ready write data!");
            Thread.sleep((long)(Math.random()*1000));
            this.data = data;
            System.out.println(Thread.currentThread().getName() + "have be write data " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwl.writeLock().unlock();
        }
    }
}
