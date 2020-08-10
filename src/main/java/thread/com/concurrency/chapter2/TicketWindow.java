package thread.com.concurrency.chapter2;


/**
 * @author admin
 * @title: TicketWindow
 * @projectName base_java
 * @description: 银行柜台叫号
 * @date 2020/7/26 16:35
 *
 * 第一种方式：把最大号和所叫叫的号码设置为静态成员变量，
 * 所有的线程共用这个数据
 *
 */
public class TicketWindow  extends Thread {

    public String name;
    private static final int MAX = 50;

    private static int index =1;

    public TicketWindow(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while(index <= MAX){
            System.out.println("柜台："+ name +"当前的号码是："+ index++);
        }
    }
}
