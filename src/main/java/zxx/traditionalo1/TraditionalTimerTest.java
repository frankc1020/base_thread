package zxx.traditionalo1;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器
 */
public class TraditionalTimerTest {
    private static int count = 0;
    public static void main(String[] args) {
        //两种定时器：
        /**
         * 第一种：等待多长时间执行
         *
         */
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("bombong");
            }
        },3000);
        /**
         * 第二种：等待多长时间执行，执行之后每隔定长的时间执行
         */
       /* new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("bombong");
            }
        },10000,3000);*/



        //每隔固定时间连续不停的执行
        class MyTimerTask extends  TimerTask{
            @Override
            public void run() {
                count = (count+1)%2;
                System.out.println("bombing");
                new Timer().schedule(new MyTimerTask(),2000+2000*count);
            }
        }

        new Timer().schedule(new MyTimerTask(),2000);
        while(true){
            System.out.println(new Date().getSeconds());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
