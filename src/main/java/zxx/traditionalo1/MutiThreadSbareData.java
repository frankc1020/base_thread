package zxx.traditionalo1;

/**
 * 多个线程访问共享对象和数据的方式
 * 两个线程操作j 每次+1
 * 两个线程操作j 每次-1
 */
public class MutiThreadSbareData {


    public static void main(String[] args) {
        ShareData data1 = new ShareData();

        new Thread(new MyRunnable1(data1)).start();
        new Thread(new MyRunnable2(data1)).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                data1.increment();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                data1.decrement();
            }
        }).start();
    }


}

class MyRunnable1 implements Runnable{

    private final ShareData data1;

    public MyRunnable1(ShareData data1){
        this.data1 = data1;
    }

    @Override
    public void run() {
        data1.increment();
    }
}

class MyRunnable2 implements Runnable{

    private final ShareData data2;

    public MyRunnable2(ShareData data1){
        this.data2 = data1;
    }

    @Override
    public void run() {
        data2.decrement();
    }
}

class ShareData /*implements Runnable*/{
    private int j = 0;
    public synchronized void increment(){
        j++;
    }

    public synchronized void decrement(){
        j--;
    }
}
