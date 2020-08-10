package thread.com.concurrency.chapter3;

/**
 * @author admin
 * @title: CreateThread03
 * @projectName base_java
 * @description: TODO
 * @date 2020/7/30 14:56
 */
public class CreateThread03 {
    private int i = 0;

    private byte[] bytes = new byte[1024];

    private static int counter = 0;

    //JVM will create a thread named "main"
    public static void main(String[] args) {
        //create a JVM stack
        try {
            add(0);
        }catch (Error e){
            e.printStackTrace();
            System.out.println(counter);
        }
    }

    private static void add(int i){
        ++counter;
        add(i+1);
    }

}
//StackOverflowError
//21456
