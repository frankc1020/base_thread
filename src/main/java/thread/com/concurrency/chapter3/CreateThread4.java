package thread.com.concurrency.chapter3;

/**
 * @author admin
 * @title: CreateThread4
 * @projectName base_java
 * @description: TODO
 * @date 2020/7/30 15:29
 *
 * //带有线程栈内存的创建模式
 *
 */
public class CreateThread4 {

    private static int counter = 1;

    public static void main(String[] args) {
        Thread t1 = new Thread(null, new Runnable() {
            @Override
            public void run() {
                try {
                    add(1);
                }catch (Error e){
                    System.out.println(counter);
                }

            }

            private void add(int i){
                counter++;
                add(i+1);
            }
        },"Test",1<<24);
        t1.start();
    }

}
