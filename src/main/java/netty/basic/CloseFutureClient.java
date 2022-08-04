package netty.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;
@Slf4j
public class CloseFutureClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost",8080));
        Channel channel = channelFuture.channel();
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while(true){
                String line = scanner.nextLine();
                if("q".equals(line)){
                    channel.close();//close是异步操作，所以在关闭之后的操作不能写在close方法后面
                    break;
                }
                channel.writeAndFlush(line);
            }
        },"input").start();

        //获取 ColsedFuture 对象，1）同步处理关闭  2） 异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();
//        System.out.println("waiting close...");
//        closeFuture.sync();
//        log.info("处理关闭之后的数据");
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                log.info("处理关闭之后的数据");
                group.shutdownGracefully();
            }
        });
    }
}
