package demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.SQLOutput;


public class shunxuduceshi {

    public static void main(String[] args) {
        String aaa = fileRead("C:\\test\\create\\ccdv\\",1000000);
        System.out.println(aaa);
    }

    public static String fileRead(String filePath, long index) {
        File file = new File(filePath+"ccdv0.log");
//        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        MappedByteBuffer map;
        try {
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, index);
            byte[] byteArr = new byte[10 * 1024];
            map.get(byteArr, 0,9 );
            return new String(byteArr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "";
    }


}
