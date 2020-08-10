package zxx.traditionalo1;

public class TranditionalThreadSynchornized {
    public static void main(String[] args) {
        new TranditionalThreadSynchornized().init();
    }

    private void init(){
        Outputer outputer = new Outputer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output("zhangxiaoxiang");
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output("lihuoming");
                }
            }
        }).start();
    }
     static class Outputer{
        public void output(String name){
            int len = name.length();
            /**
             * 若想要使该方法与方法三同步，则需把该方法的
             * 同步对象this换成 Outputer.class
             */
            synchronized (this){//互斥
                for(int i=0;i<len;i++){
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }
        }

         /**
          * 互斥方法
          * @param name
          */
         public synchronized void output2(String name){
             int len = name.length();
             synchronized (this){//互斥
                 for(int i=0;i<len;i++){
                     System.out.print(name.charAt(i));
                 }
                 System.out.println();
             }
         }

         /**
          * 同步方法三
          * @param name
          */
         public static synchronized void output3(String name){
             int len = name.length();
             for(int i=0;i<len;i++){
                 System.out.print(name.charAt(i));
             }
             System.out.println();
         }

    }
}
