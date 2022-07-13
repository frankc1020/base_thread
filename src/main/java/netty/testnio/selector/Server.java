package netty.testnio.selector;

import lombok.extern.slf4j.Slf4j;
import netty.api.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * selector
 *  事件的类型：
 *      accept： 会在有链接请求时触发
 *      connect：是客户端链接建立后触发的事件
 *      read：可读事件
 *      write：可写事件
 *
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws Exception {
        //1. 创建Selector，管理多个channel
        Selector selector = Selector.open();

        //创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        //2。建立selector 和channel的联系（注册）
        //SelectionKey 就是将来时间发生后，通过它可以知道事件和哪个channel事件联系
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key:{}",sscKey);

        //绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        while(true){
            //3.select 方法,没有事件发生，线程阻塞，有事件发生，线程才会恢复运行
            // select 方法，在时间未处理时，它不会阻塞,事件发生后，要么处理，要么取消，不能置之不理
            selector.select();
            //4.处理事件,selectedKeys 内部包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                log.debug("key:{}",sscKey);
                //处理key时，要从SelectedKeys 集合中删除，否则下次处理就会有问题
                iter.remove();
                // 5. 区分时间类型
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    // attachment
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    //将ByteBuffer 作为附件关联到 SelectionKey 上
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("sc:{}",sc);
                }else if (key.isReadable()){//读取事件
                    try {
                        //拿到触发时间的channel
                        SocketChannel channel = (SocketChannel)key.channel();
//                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        //获取SelectionKey  上关联的附件
                        ByteBuffer buffer = (ByteBuffer)key.attachment();
                        //消息边界测试
                        int read = channel.read(buffer);
                        if(read == -1){
                            //正常断开
                            key.cancel();
                        }else {
//                            buffer.flip();
////                            ByteBufferUtil.debugRead(buffer);
//                            //当解析中文时，如果超过边界，则可能会出现乱码
//                            System.out.println(Charset.defaultCharset().decode(buffer));
                            split(buffer);
                            if(buffer.position() == buffer.limit()){
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity()*2);
                                buffer.flip();
                                newBuffer.put(buffer);//0123456789abcdef3333\n
                                key.attach(newBuffer);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
//                        // 因为客户端断开了，因此需要将key 取消
//                        // （从selector 的 keys集合中真正删除 key）
                        key.cancel();
                    }
                }
                //事件取消之后，则也不会在进行循环处理
//                key.cancel();
            }
        }
    }

    private static void split(ByteBuffer source) {
        source.flip();

        for (int i = 0; i < source.limit(); i++) {
            //找到一条完整信息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                //把这条消息，存入新的ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从source 读，向target写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                ByteBufferUtil.debugAll(target);
            }
        }
        source.compact();
    }
}
