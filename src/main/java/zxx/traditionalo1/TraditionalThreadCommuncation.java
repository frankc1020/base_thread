package zxx.traditionalo1;

public class TraditionalThreadCommuncation {
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
        private boolean bShouldSub = true;
        public synchronized  void sub(int i){
            while(!bShouldSub){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j=1;j<=10;j++){
                System.out.println("sub Thread sequece of " + j + ",loop of " + i);
            }
            bShouldSub = false;
            this.notify();
        }

        public synchronized void main(int i){
            while(bShouldSub){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int j=1;j<=10;j++){
                System.out.println("main thread sequece of " + j + ",loop of " + i);
            }
            bShouldSub = true;
            this.notify();
        }
    }
}
