package netty.api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class TestFileWalkFileTree {

    /**
     * 遍历文件夹下有多少个文件，有多少个目录
     * @throws IOException
     */
    @Test
    public void testFileWalkFileTree() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();

        Files.walkFileTree(Paths.get("/Users/guzhaocong/mySoftware/apache-tomcat-8.5.73"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("======>"+dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("dir count:" + dirCount);
        System.out.println("file count:" + fileCount);
    }

    /**
     * 测试删除功能
     */
    @Test
    public void testDelWalkFileTree()throws IOException{
        Files.walkFileTree(Paths.get("/Users/guzhaocong/logs/metrics"),new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    /**
     * 多级目录拷贝
     */
    @Test
    public void testCopyWalkFile() throws IOException {
        String source = "/Users/guzhaocong/plugin/idea-jihuo";
        String target = "/Users/guzhaocong/logs/idea-jihuo";
        Files.walk(Paths.get(source)).forEach(path -> {
            String targetName = path.toString().replace(source,target);
            if(Files.isDirectory(path)){
                try {
                    Files.createDirectory(Paths.get(targetName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(Files.isRegularFile(path)){
                try {
                    Files.copy(path,Paths.get(targetName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
