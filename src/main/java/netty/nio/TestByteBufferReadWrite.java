package netty.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

import static netty.nio.ByteBufferUtil.debugAll;

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
         *  HeapByteBuffer  - Java 堆内存，读写效率较低，收到GC的影响
         *  DirectByteBuffer - 直接内存，读写效率高（少一次拷贝），不会收GC的影响
         */
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());

    }
}
