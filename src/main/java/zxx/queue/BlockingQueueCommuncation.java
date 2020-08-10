package zxx.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueCommuncation {
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
        BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
        BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);
        {
            try {
                queue2.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public  void sub(int i){

                try {
                    queue1.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            for (int j=1;j<=10;j++){
                System.out.println("sub Thread sequece of " + j + ",loop of " + i);
            }
            try {
                queue2.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void main(int i){
            try {
                queue2.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int j=1;j<=10;j++){
                System.out.println("main thread sequece of " + j + ",loop of " + i);
            }
            try {
                queue1.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
