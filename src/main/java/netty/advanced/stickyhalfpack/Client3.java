package netty.advanced.stickyhalfpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author admin
 * @title: HelloWorldClient
 * @projectName base_thread
 * @description: TODO
 * @date 2022/8/9 13:25
 */
@Slf4j
public class Client3 {
    public static void main(String[] args) {
        send();
//        System.out.println(new String(fill10Bytes('1',5)));
        log.info("finish");
    }

    /**
     * 定长解码
     * @param c
     * @param len
     * @return
     */
    public  static  StringBuilder makeString(char c,int len){
        StringBuilder sb = new StringBuilder(len+2);
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        sb.append("\n");
        return sb;
    }

    private static void send() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        // 会在连接 channel 建立成功后，会触发active事件
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            char c = '0';
                            Random r = new Random();
                            ByteBuf buf = ctx.alloc().buffer();
                            for (int i = 0; i < 10; i++) {
                                StringBuilder str = makeString(c,r.nextInt(256)+1);
                                c++;
                                buf.writeBytes(str.toString().getBytes());
                            }
                            ctx.writeAndFlush(buf);
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8080).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("client error",e);
        }finally {
            worker.shutdownGracefully();
        }
    }
}
