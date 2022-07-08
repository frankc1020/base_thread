package netty.api;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestScatteringReads {
    /**
     * 分散读
     */
    @Test
    public void testScatteringReads(){
        try (FileChannel channel = new RandomAccessFile("src/main/java/netty/test/file/words.txt", "r").getChannel()) {
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{b1,b2,b3});
            b1.flip();
            b2.flip();
            b3.flip();
            ByteBufferUtil.debugAll(b1);
            ByteBufferUtil.debugAll(b2);
            ByteBufferUtil.debugAll(b3);
        } catch (IOException e) {
        };
    }

    /**
     * 集中写
     * @throws FileNotFoundException
     */
    @Test
    public void testGatheringWrites() throws FileNotFoundException {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("您好");
        try (FileChannel channel = new RandomAccessFile("src/main/java/netty/test/file/words2.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{b1,b2,b3});
        } catch (IOException e) {
        }
    }

    /**
     * 黏包半包分析
     * 网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
     * 但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
     *
     * * Hello,world\n
     * * I'm zhangsan\n
     * * How are you?\n
     *
     * 变成了下面的两个 byteBuffer (黏包，半包)
     *
     * * Hello,world\nI'm zhangsan\nHo
     * * w are you?\n
     *
     * 现在要求你编写程序，将错乱的数据恢复成原始的按 \n 分隔的数据
     */
    @Test
    public void testByteBufferExam(){
        ByteBuffer source =  ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private void split(ByteBuffer source) {
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

    /**
     * 文件复制
     * 效率也比文件输入流，输出流高
     */
    @Test
    public void testFileChannelTransferTo(){
        try (
                FileChannel from = new FileInputStream("src/main/java/netty/test/file/words2.txt").getChannel();
                FileChannel to = new FileOutputStream("src/main/java/netty/test/file/to.txt").getChannel();
        ) {
            // 效率高，只要使用 transferTo 底层会利用操作系统的零拷贝进行优化
            from.transferTo(0,from.size(),to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 大文件 传输时，
     */
    @Test
    public void testFileChannelTransferToBigFile(){
        try (
                FileChannel from = new FileInputStream("src/main/java/netty/test/file/words2.txt").getChannel();
                FileChannel to = new FileOutputStream("src/main/java/netty/test/file/to.txt").getChannel();
        ) {
            // 效率高，只要使用 transferTo 底层会利用操作系统的零拷贝进行优化
            //2g 数据
            long size = from.size();
            for(long left = size;left >0;){
                System.out.println("position:"+(size-left) + " left:"+left);
                left -= from.transferTo((size-left),size,to);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPathAndFile(){
        Path path = Paths.get("src/main/java/netty/test/file/words.txt");
        System.out.println(path);
        if(Files.exists(path)){
            System.out.println(path);
        }else{
            System.out.println("未检测到");
        }
    }

}
