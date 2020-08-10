package thread.com.concurrency.chapter1;

public class TryConcurrency {
    public static void main(String[] args) {
        new Thread("read-Thread"){
            @Override
            public void run() {
                readFromDataBase();
            }
        }.start();
        new Thread("read-Thread"){
            @Override
            public void run() {
                writeDataToFile();
            }
        }.start();

    }

    private static void readFromDataBase(){
        //read data from database and handle it
        try{
            println("Begin read data from db.");
            Thread.sleep(1000 * 10L);
            println("Read data done and start and handle it.");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        println("The data handle finish and successfully.");
    }

    private static void writeDataToFile(){
        try{
            println("Begin write data to File.");
            Thread.sleep(2000 * 10L);
            println("Write data done and start handle it.");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        println("The data handle finish and successfully.");
    }

    private static void println(String message){
        System.out.println(message);
    }
}
