package messageQueue;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static messageQueue.FileMessageEntity.ByteArraytoInt;

/**
 * 消息队列顺序读写工具类
 */
@Slf4j
public class FileIndexReadUtile {
    private Long position;
    private int index;
    private int lastPosition = 0;
    private RandomAccessFile randomAccessFile;
    private MappedByteBuffer mappedByteBuffer;
    private FileChannel targeFileChannel;
    private Long defSize = (long)10*1024*1024;
    private String filePath;
    private TopicEntity topicEntity;

    public FileIndexReadUtile() {
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
    public FileIndexReadUtile(String filePath, TopicEntity topicEntity, int index) {
        this.topicEntity = topicEntity;
        this.filePath = filePath;
        // TODO yingdang duqusuoyinwenjian queding index
        this.index = index;
        putFileMessage(index);

    }

    public long myfileWrite(byte[] message) {

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

            File file = new File(filePath+this.topicEntity.topic+ index+".ind");
            randomAccessFile = new RandomAccessFile(file,"rw");
            targeFileChannel = randomAccessFile.getChannel();
            mappedByteBuffer = targeFileChannel.map(FileChannel.MapMode.READ_WRITE,position,defSize-position);
            this.index = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileIndexEntity fileRead( ) {

//        byte[] msgBytes = messages.getBytes("utf-8");
//        byte[] lenghtBts = InttoByteArray(lenght);
//        byte[] setoffBts = InttoByteArray(setoff);
        byte[] setoffBts = new byte[4];
        mappedByteBuffer.get(setoffBts, 0, 4);
        byte[] indexpartitionBts = new byte[4];
        mappedByteBuffer.get(indexpartitionBts, 0, 4);
        int setof = ByteArraytoInt(setoffBts);
        int indexpartition = ByteArraytoInt(indexpartitionBts);
        return new FileIndexEntity(setof,indexpartition);

    }

    public int getPosition(int setoff ) {
        try {
            while (true){
                byte[] setoffBts = new byte[4];
                mappedByteBuffer.get(setoffBts, 0, 4);
                int setof = ByteArraytoInt(setoffBts);
                byte[] indexpartitionBts = new byte[4];
                mappedByteBuffer.get(indexpartitionBts, 0, 4);
                int indexpartition = ByteArraytoInt(indexpartitionBts);
                if (setof >= setoff){
                    return lastPosition;
                }
                lastPosition = indexpartition;
            }

        }catch (Exception e){
            return 0;
        }
//        byte[] msgBytes = messages.getBytes("utf-8");
//        byte[] lenghtBts = InttoByteArray(lenght);
//        byte[] setoffBts = InttoByteArray(setoff);

    }


    public void getMmp(long position){
        try {
            File file = new File(filePath + topicEntity.topic + index + ".log");
            randomAccessFile = new RandomAccessFile(file,"rw");
            targeFileChannel = randomAccessFile.getChannel();
            mappedByteBuffer = targeFileChannel.map(FileChannel.MapMode.READ_WRITE,position,defSize-position);
            this.setIndex((int) position);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
