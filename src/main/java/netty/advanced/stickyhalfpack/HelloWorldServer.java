package netty.advanced.stickyhalfpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author admin
 * @title: HelloWorldServer
 * @projectName base_thread
 * @description: TODO
 * @date 2022/8/9 13:19
 */
@Slf4j
public class HelloWorldServer {
    void start(){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            /**
             *  接收端可以接收160B的数据，客户端每次发送16B数据，发送10次，但是接收端一次性接收了客户端发送10次的数据，即160B的数据
             *  这就是黏包现象
             *
             *  半包现象就是 接收端可以接收80B的数据，但是客户端端一次发送了120B的数据，这就会导致接收端会接收到一个80B的数据，一个40B的数据，
             *  分为两次接收，这就是半包现象
             */
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 调整系统的接收缓冲器（滑动窗口）
//            serverBootstrap.option(ChannelOption.SO_RCVBUF,10);
            // 调整netty的接收缓冲区（byteBuf）
            serverBootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(16,16,16));

            serverBootstrap.group(boss,worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    //定长解码器
//                    ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
                    //行解码器
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8080)
                    .sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
           log.error("server error",e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new HelloWorldServer().start();
    }
}
