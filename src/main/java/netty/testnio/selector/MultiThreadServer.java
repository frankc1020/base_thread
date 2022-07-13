package netty.testnio.selector;

import lombok.extern.slf4j.Slf4j;
import netty.api.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        //1.创建固定数量的worker
        Worker[] workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
             workers[i] = new Worker("worker-"+i);
        }
        AtomicInteger index = new AtomicInteger();
        while(true){
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                iter.remove();
                if(key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected...{}",sc.getRemoteAddress());
                    //2. 关联selector
                    log.debug("before register...{}",sc.getRemoteAddress());
                    // round robin 轮询
                    workers[index.getAndIncrement() % workers.length].register(sc);

                    log.debug("after register...{}",sc.getRemoteAddress());
                }
            }
        }

    }
    static class  Worker implements Runnable{
        private Thread thread;

        private Selector selector;

        private String name;

        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();
        private boolean start = false;//还未初始化

        public Worker(String name) {
            this.name = name;
        }
        //初始化线程 和 selector
        public void register(SocketChannel sc) throws IOException {
            if(!start){
                thread = new Thread(this,name);
                thread.start();
                selector = Selector.open();
                start = true;
            }
            //向队列中添加了任务，但是这个任务并没有立刻执行
            queue.add(()->{//两个线程之间，需要某个线程在一个线程某个地方在执行，使用队列，则轻松实现
                try {
                    sc.register(selector,SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            // 在没有注册监听事件时，即第一次使用该方法主动唤醒，过了select方法的阻塞，使程序往下执行，注册读事件，
            // 这时，下面可能不会出来数据，当程序再次回到select的时候，被阻塞，当客户端发送过来数据，
            // 因为读事件已被注册，故可继续执行
            selector.wakeup();//唤醒select 方法
        }

        @Override
        public void run() {
            while(true){
                try {
                    selector.select();
                    Runnable task = queue.poll();
                    if(task != null){
                        task.run();//在这里执行了注册 sc.register(selector,SelectionKey.OP_READ);
                    }
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()){
                        SelectionKey key = iter.next();
                        iter.remove();
                        if(key.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel)key.channel();
                            log.debug("read...{}",channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
