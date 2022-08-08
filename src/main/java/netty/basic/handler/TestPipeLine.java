package netty.basic.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import netty.dto.Student;

import java.nio.charset.Charset;

/**
 * @author admin
 * @title: TestPipeLine
 * @projectName base_thread
 * @description: TODO
 * @date 2022/8/4 14:30
 */
@Slf4j
public class TestPipeLine {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //1. 通过 channel 拿到 pipeline
                        ChannelPipeline pipeline = ch.pipeline();
                        //2. 添加处理器 head ->  h1 -> h2 -> h3 -> h4 -> h5 -> h6 -> tail
                        pipeline.addLast("h1",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                String name = buf.toString(Charset.defaultCharset());
                                log.info("1,转换结果：{}",name);
                                super.channelRead(ctx, name);
                            }
                        });
                        pipeline.addLast("h2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object name) throws Exception {

                                Student student = new Student(name.toString());
                                log.info("2,转换结果：{}",student);
                                super.channelRead(ctx, student);
                            }
                        });
                        pipeline.addLast("h3",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("3,结果:{},class:{}",msg,msg.getClass());
                                //channelRead 将数据传递给下个 handler，如果不调用，调用链会断开
//                                super.channelRead(ctx, msg);
                                //只用往channel里面写入数据，才会触发出栈处理器
                                // NioSocketChannel 则是从pipeLine的结尾开始寻找出栈处理器
//                                ch.writeAndFlush(ctx.alloc().buffer().writeBytes("NioSocketChannel...".getBytes()));
                                // ChannelHandlerContext 是从当前的入栈处理器往前找出栈处理器
                                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("ChannelHandlerContext...".getBytes()));
                            }
                        });

                        pipeline.addLast("h4",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("4");
                                super.write(ctx, msg, promise);
                            }
                        });
                        pipeline.addLast("h5",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("5");
                                super.write(ctx, msg, promise);
                            }
                        });
                        pipeline.addLast("h6",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("6");
                                super.write(ctx, msg, promise);
                            }
                        });

                    }
                })
                .bind(8080);
    }
}
