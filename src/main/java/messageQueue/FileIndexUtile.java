package messageQueue;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 消息队列顺序读写工具类
 */
@Slf4j
public class FileIndexUtile {
    private Long position;
    private int index;
    private int baseSetoff;
    private RandomAccessFile randomAccessFile;
    private MappedByteBuffer mappedByteBuffer;
    private FileChannel targeFileChannel;
    private Long defSize = (long)10*1024*1024;
    private String filePath;
    private TopicEntity topicEntity;

    public FileIndexUtile() {
//        File file = new File(filePath+topic+".log");
//        try {
//            randomAccessFile = new RandomAccessFile(file,"rw");
//            targeFileChannel = randomAccessFile.getChannel();
//            mappedByteBuffer = targeFileChannel.map(FileChannel.MapMode.READ_WRITE,0,defSize);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public FileIndexUtile(String filePath, TopicEntity topicEntity,int baseSetoff, int index) {
        this.topicEntity = topicEntity;
        this.filePath = filePath;
        this.baseSetoff = baseSetoff;
        // TODO yingdang duqusuoyinwenjian queding index
        this.index = index;
        putFileMessage(index);

    }

    public long myfileWrite(byte[] message) {

        if (index == defSize){
            log.info("777777777777777777777777777777777777777777777");
        }
            mappedByteBuffer.position(index);
            mappedByteBuffer.put(message);
//            targeFileChannel.close();
//            randomAccessFile.close();
            return this.index = mappedByteBuffer.position();

    }
    public long subbyte() throws IOException {

        targeFileChannel.truncate(index);
        return this.index = mappedByteBuffer.position();

    }
    public void setIndex(int indes){
        this.index = indes;
    }

    private void putFileMessage(long position){
        try {

            File file = new File(filePath+this.topicEntity.topic+ baseSetoff+".ind");
            randomAccessFile = new RandomAccessFile(file,"rw");
            targeFileChannel = randomAccessFile.getChannel();
            mappedByteBuffer = targeFileChannel.map(FileChannel.MapMode.READ_WRITE,position,defSize);
            this.index = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static long fileWrite(String filePath, String content, int index) {
        File file = new File(filePath);
        RandomAccessFile randomAccessFile;
        MappedByteBuffer mappedByteBuffer;
        try {
            randomAccessFile = new RandomAccessFile(file,"rw");
            FileChannel targeFileChannel = randomAccessFile.getChannel();
            mappedByteBuffer = targeFileChannel.map(FileChannel.MapMode.READ_WRITE,0,(long)1024*1024*1024);
            mappedByteBuffer.position(index);
            mappedByteBuffer.put(content.getBytes());
            targeFileChannel.close();
            randomAccessFile.close();
            return mappedByteBuffer.position();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static String fileRead(String filePath, long position, long index) {
        File file = new File(filePath);
        RandomAccessFile randomAccessFile;
        MappedByteBuffer mappedByteBuffer;

        try {
            randomAccessFile = new RandomAccessFile(file,"rw");
            FileChannel targeFileChannel = randomAccessFile.getChannel();
            mappedByteBuffer = targeFileChannel.map(FileChannel.MapMode.READ_ONLY,position,index);
            byte[] bytes = new byte[10*1024];
            mappedByteBuffer.get(bytes,0, (int) index);
            return new String(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
