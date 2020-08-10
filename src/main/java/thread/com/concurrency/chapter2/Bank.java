package thread.com.concurrency.chapter2;

/**
 * @author admin
 * @title: Bank
 * @projectName base_java
 * @description: TODO
 * @date 2020/7/26 16:38
 */
public class Bank {
    public static void main(String[] args) {
        TicketWindow ticketWindow1 = new TicketWindow("一号柜台");
        ticketWindow1.start();

        TicketWindow ticketWindow2 = new TicketWindow("二号柜台");
        ticketWindow2.start();

        TicketWindow ticketWindow3 = new TicketWindow("三号柜台");
        ticketWindow3.start();
    }
}
