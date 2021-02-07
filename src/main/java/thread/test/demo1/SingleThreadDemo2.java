package thread.test.demo1;

/**
 * @author admin
 * @title: SingleThreadDemo2
 * @projectName base_thread
 * @description: TODO
 * @date 2021/2/7 21:24
 * 静态内部类：
 * 1. 这种方式采用了类装载机制来保证初始化实例时只有一个线程
 * 2. 静态内部类方式在SingleThreadDemo2类装载时并不会立即实例化，而是在需要实例化时，
 * 调用getInstance方法，才会装载InnerDemo类，从而完成SingleThreadDemo2 的实例化。
 * 3. 类的静态属性只会在第一次加载类的时候完成初始化，所以在这里，JVM帮助我们保证了线程的
 * 安全性，在类进行初始化时，别的线程是无法进入的。
 * 4. 优点： 避免了线程不安全，利用静态内部类特点实心延迟加载，效率高，推荐使用。
 */
public class SingleThreadDemo2 {

    private SingleThreadDemo2(){}

    static class InnerDemo{
        private static final SingleThreadDemo2 instance = new SingleThreadDemo2();
    }
    public static SingleThreadDemo2 getInstance(){
        return InnerDemo.instance;
    }

}
