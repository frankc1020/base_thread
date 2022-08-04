package netty.basic.future;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @title: TestNettypromise
 * @projectName base_thread
 * @description: TODO
 * @date 2022/8/4 09:42
 */
@Slf4j
public class TestNettypromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //1. 准备EventLoop 对象
        EventLoop eventLoop = new NioEventLoopGroup().next();

        //2. 可以主动创建 promise,结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
            //3. 任意一个线程执行计算，计算完毕向promise填充结果
            log.info("开始计算");
            //暂停一会线程
            try {
                int i = 1 / 0;
                TimeUnit.SECONDS.sleep(1);
                promise.setSuccess(80);
            } catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }

        }).start();

        //4.接收结果的线程
        log.info("等待结果。。。");
        log.info("结果是：{}",promise.get());
    }
}
