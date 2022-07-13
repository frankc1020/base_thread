package netty.testnio.c4;

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
 * nio 非阻塞模式
 */
@Slf4j
public class Server2 {
    public static void main(String[] args) throws Exception {
        //0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //1. 创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //默认是阻塞模式，false，则切换为非阻塞模式
        ssc.configureBlocking(false);
        //2.绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        List<SocketChannel> channels = new ArrayList<>();
        while(true){
            //3. accept 建立与客户端的链接,SocketChannel 用来与客户端之间通信
//            log.debug("connecting....");
            SocketChannel sc = ssc.accept();// 非阻塞，线程还会继续运行，如果没有链接建立，但sc是null
            if(sc != null){
                log.debug("connected....{}",sc);
                //非阻塞模式，默认是true，阻塞模式
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for (SocketChannel channel : channels) {
                //接收客户端发送的数据
                log.debug("before read...{}",channel);
                //设置 configureBlocking=false 则read为 非阻塞，线程仍然会继续运行，如果没有读到数据，会返回0
                int read = channel.read(buffer);
                if(read > 0){
                    buffer.flip();
                    ByteBufferUtil.debugRead(buffer);
                    buffer.clear();
                    log.debug("after read...{}",channel);
                }
            }
        }
    }
}
