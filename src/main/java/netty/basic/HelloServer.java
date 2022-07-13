package netty.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        //1. 启动器，负责组装netty 组件，启动服务器
        new ServerBootstrap()
                // BossEventLoop, WorkerEventLoop(selector,thread),group 组
                .group(new NioEventLoopGroup())
                // 3.选择服务器的 ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class)
                //4. boss 负责处理连接 worker(child) 负责处理读写,决定worker(child) 能执行哪些操作(handler)
                .childHandler(
                        //5. 代表 和客户端进行数据读写的通道 Initializer 初始化，负责添加handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //添加具体handler
                        ch.pipeline().addLast(new StringDecoder());//将ByteBuf 转换为字符串
                        //ChannelInboundHandlerAdapter 自定义handler
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            //读事件
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                //绑定监听端口
                .bind(8080);
    }
}
