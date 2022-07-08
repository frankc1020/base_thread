package netty.api;


import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static netty.api.ByteBufferUtil.debugAll;
public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);
        debugAll(buffer);

        buffer.put(new byte[]{0x62,0x63,0x64});// b c d
        debugAll(buffer);
        buffer.flip();
        System.out.println(buffer.get());
        debugAll(buffer);
        buffer.compact();
        debugAll(buffer);
        buffer.put(new byte[]{0x65,0x66});
        debugAll(buffer);
    }

    @Test
    public void testByteBufferAllocate(){
        /**
         *class java.nio.DirectByteBuffer - Java 堆内存，读写效率较低，收到GC的影响
         *class java.nio.DirectByteBuffer  - 直接内存，读写效率高（少一次拷贝），不会收GC的影响，分配的效率低
         */
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());

    }
    @Test
    public void testByteBufferRead(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();

        // rewind 从头开始读
//        buffer.get(new byte[4]);
//        debugAll(buffer);
//        buffer.rewind();
//        System.out.println((char)buffer.get());

        //mark  & reset
        //mark 做一个标记，记录 position位置，reset 是将position 位置，重置到 mark 的位置
//        System.out.println((char)buffer.get());
//        System.out.println((char)buffer.get());
//        buffer.mark(); // 加标记， 索引2的位置
//        System.out.println((char)buffer.get());
//        System.out.println((char)buffer.get());
//        buffer.reset();// 将position 重置到索引2
//        System.out.println((char)buffer.get());
//        System.out.println((char)buffer.get());

        // get(i) 不会改变索引的位置
        System.out.println((char)buffer.get(3));
        debugAll(buffer);
    }

    /**
     * 字符串转为ByteBuffer
     */
    @Test
    public void testByteBufferString(){
        //字符串转为ByteBuffer
        ByteBuffer buffer  = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());
        debugAll(buffer);

        //2. Charset
        ByteBuffer hello = StandardCharsets.UTF_8.encode("hello");
        debugAll(hello);

        //3. wrap
        ByteBuffer wrap = ByteBuffer.wrap("hello".getBytes());
        debugAll(wrap);

        //转为字符串
        String str = StandardCharsets.UTF_8.decode(hello).toString();
        System.out.println(str);
    }
}
