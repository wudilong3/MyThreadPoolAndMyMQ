package demo;

import lombok.extern.slf4j.Slf4j;
import messageQueue.*;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 消息队列顺序读写工具类
 */
@Slf4j
public class MessageQueueServerUtile {
    private Long position;
    private int index;
    private int baseSetoff;
    private RandomAccessFile randomAccessFile;
    private MappedByteBuffer mappedByteBuffer;
    private FileChannel targeFileChannel;
    private Long defSize = (long)1024*1024*1024;
    private String filePath;
    private TopicEntity topicEntity;
    private int indexOneSize;
    private FileIndexQueueEntity fileIndexQueueEntity;
    FileIndexUtile fileIndexUtile;


    public MessageQueueServerUtile() {
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
    public MessageQueueServerUtile(String filePath, TopicEntity topicEntity, int baseSetoff, int index) {
        this.topicEntity = topicEntity;
        this.filePath = filePath;
        this.baseSetoff = baseSetoff;
        // TODO yingdang duqusuoyinwenjian queding index
        this.index = index;
        putFileMessage();

    }

    public long myfileWrite(FileMessageEntity fileMessageEntity) throws UnsupportedEncodingException {


            mappedByteBuffer.position(index);
            mappedByteBuffer.put(fileMessageEntity.getBytes());

//            targeFileChannel.close();
//            randomAccessFile.close();
        this.index = mappedByteBuffer.position();
        if (index == defSize){
            baseSetoff = fileMessageEntity.setoff;
            putFileMessage();
        }
        if (index>=(indexOneSize+800)) {
            FileIndexEntity fileIndexEntity = new FileIndexEntity(fileMessageEntity.setoff, index);
            fileIndexUtile.myfileWrite(fileIndexEntity.getBytes());
        }
            return index;

    }
    public void setIndex(int indes){
        this.index = indes;
    }

    private void putFileMessage(){
        try {
            File dire = new File(filePath);
            if (!dire.exists()) {
                dire.mkdirs();
            }
            File file = new File(filePath+this.topicEntity.topic+baseSetoff+".log");
            fileIndexUtile = new FileIndexUtile(filePath,topicEntity,baseSetoff,0);
            randomAccessFile = new RandomAccessFile(file,"rw");
            targeFileChannel = randomAccessFile.getChannel();
            mappedByteBuffer = targeFileChannel.map(FileChannel.MapMode.READ_WRITE,0,defSize);
            this.index = 0;
            this.indexOneSize = 0;
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
