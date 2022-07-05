package netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class TestByteBuffer {
    /**
     * ByteBuffer 正确使用姿势
     *  1.向buffer写入数据，例如调用channel.read(buffer)
     *  2.调用flip()切换至读模式
     *  3.从buffer读取数据，例如调用buffer.get()
     *  4.调用clear() 或 compact() 切换至写模式
     *  5.重复 1-4 步骤
     * @param args
     */
    public static void main(String[] args) {
        //FileChannel
        /**
         * 获取方式：
         *  1. 输入输出流获取
         *  2.RandomAccessFile
         */
        try(FileChannel channel = new FileInputStream("src/main/java/netty/test/file/data.txt").getChannel()) {
            //准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);

            while(true){
                //从 channel 读取数据，向buffer 写入
                int len = channel.read(buffer);
                log.debug("读取到的字节数:{}",len);
                if(len == -1){//没有读取到内容
                    break;
                }
                //打印 buffer 的内容
                buffer.flip();//切换到buffer的读模式
                while(buffer.hasRemaining()){//hasRemaining 检查buffer是否还有剩余的未读数据
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                //读完一次以后，buffer切换为写模式
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
