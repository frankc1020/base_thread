package netty.testnio.c4;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import netty.api.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * nio 阻塞模式
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        //使用nio 来理解阻塞模式
        //0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //1. 创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //2.绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        List<SocketChannel> channels = new ArrayList<>();
        while(true){
            //3. accept 建立与客户端的链接,SocketChannel 用来与客户端之间通信
            log.debug("connecting....");
            SocketChannel sc = ssc.accept();//accept 阻塞方法，线程停止运行
            log.debug("connecting....{}",sc);
            channels.add(sc);
            for (SocketChannel channel : channels) {
                try {
                    //接收客户端发送的数据
                    log.debug("before read...{}",channel);
                    //等待客户端发送消息，客户端如果不发送消息，则会阻塞在这里，等待客户端发送消息
                    channel.read(buffer);//read 方法也是阻塞方法，线程也会停止运行
                    buffer.flip();
                    ByteBufferUtil.debugRead(buffer);
                    buffer.clear();
                    log.debug("after read...{}",channel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
