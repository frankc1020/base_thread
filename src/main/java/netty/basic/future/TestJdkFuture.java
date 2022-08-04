package netty.basic.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future =  service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("执行计算");
                //暂停一会线程
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                return 50;
            }
        });

        //3. 主线程future 来获取结果
        log.info("等待结果");
        log.info("结果是 {}",future.get());
    }
}
