package netty.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * ChannelFuture 讲解
 */
@Slf4j
public class EventLoopClient2 {
    public static void main(String[] args) throws InterruptedException {
        //2. 带有Future Promise 的类型都是和异步方法配套使用，用来处理结果
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                //1. 连接到服务器
                //异步非阻塞  ，main 发起了调用，真正执行 connect是nio线程
                .connect(new InetSocketAddress("localhost", 8080));
        // 如果缺少sync方法，则客户端将不能发送消息到服务端
        //2.1 使用sync 方法同步处理结果
        /*channelFuture.sync();//阻塞住
        // 无阻塞向下执行获取channel
        Channel channel = channelFuture.channel();
        channel.writeAndFlush("hello World!");*/

        //2.2 使用 addListener(回调对象)  方法异步处理结果

        channelFuture.addListener(new ChannelFutureListener() {
            //在 nio 线程连接建立好了后，会调用
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                log.info("{}",channel);
                channel.writeAndFlush("hello World!");
            }
        });

    }
}
