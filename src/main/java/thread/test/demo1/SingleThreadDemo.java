package thread.test.demo1;

/**
 * @author admin
 * @title: SingleThreadDemo
 * @projectName base_thread
 * @description: TODO
 * @date 2021/2/5 12:25
 *
 * 双重检查机制：Double-Check
 * 1.概念是多线程开发中常使用到的，如代码所示，我们进行了两次if(instance==null)检查，这样就可以保证线程安全
 * 2.这样，实例化代码只用执行一次，后面再次访问时，判断if(instance == null),直接return实例化对象，也避免反复进行方法同步
 * 3.线程安全：延迟加载，效率较高，在实际开发中，推荐使用这种单例设计模式
 *
 *
 *
 * volatile 关键字：主要作用
 * 1.保证可见性
 * 2.不保证原子性
 * 3.禁止指令重排
 *
 * instance =  new SingleThreadDemo();不是原子操作，这段代码可以简单分为以下三步执行：
 * 1.为instance 分配内存空间
 * 2. 初始化instance
 * 3. 将instance执行分配的内存空间
 *
 * 但是由于JVM 具有指令重排的特性，执行顺序可能变成1->3->2。指令重排在单线程环境下不会出现问题，但是在多线程
 * 环境下会导致一个线程获得还没有初始化的实例。
 * 例如：线程a执行了1和3，此时线程b调用getInstance()后发现intsance不为空，因此返回instance，
 * 但是instance还没有被初始化，所以就会导致空指针异常。
 *
 */
public class SingleThreadDemo {
    private static volatile  SingleThreadDemo instance;

    private SingleThreadDemo(){}

    public static SingleThreadDemo getInstance() {
        if(instance == null){
            synchronized(SingleThreadDemo.class){
                if (instance == null){
                    instance =  new SingleThreadDemo();
                }
            }
        }
        return instance;
    }
}
