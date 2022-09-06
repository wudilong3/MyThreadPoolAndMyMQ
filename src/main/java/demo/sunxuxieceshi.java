package demo;

import messageQueue.FileMessageEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class sunxuxieceshi {

    public static void main(String[] args) {
        fileWrite("C:\\text\\","111111",0);
        int a = 1;
        return;
    }

    public static long fileWrite(String filePath, String content, int index) {
        File dire = new File(filePath);
        if (!dire.exists()) {
            dire.mkdirs();
        }
        File file = new File(filePath+"12213213.log");
//        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        MappedByteBuffer map;
        try {
            FileMessageEntity AAA = new FileMessageEntity("1",1);
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, (long) 1024 * 1024 * 1024);
            map.position(index);
            map.put(AAA.getBytes());
            return map.position();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return 0L;
    }
}
