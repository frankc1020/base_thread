package zxx.threadpool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 面试题：缓存系统
 */
public class CacheDemo {

    private Map<String,Object> cache = new HashMap<String,Object>();
    public static void main(String[] args) {

    }

    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    public Object getData(String key){//读取数据的方法
        rwl.readLock().lock();//首先上读锁
        Object value = null;
        try{
            value = cache.get(key);
            if(value == null){//当数据为空时，释放读锁，上写锁
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try{
                    if(value == null){//防止其他线程同时走到上步的写锁，不加判断的话，该线程写完释放写锁和读锁，其它线程可能会在进行写数据
                        value = "aaaa";//实际失去queryDB();
                    }
                }finally {
                    rwl.writeLock().unlock();//释放写锁在上读锁
                }
                rwl.readLock().lock();
            }
        }finally {
           rwl.readLock().unlock();
        }

        return value;
    }
}
