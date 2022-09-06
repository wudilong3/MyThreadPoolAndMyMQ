package demo;

import lombok.extern.slf4j.Slf4j;
import messageQueue.FileMessageEntity;
import messageQueue.TopicEntity;

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
public class ReadMessageQueueServerUtile {
    private Long position;
    private int index;
    private int baseSetoff;
    private RandomAccessFile randomAccessFile;
    private MappedByteBuffer mappedByteBuffer;
    private FileChannel targeFileChannel;
    private Long defSize = (long)1024*1024*1024;
    private String filePath;
    private TopicEntity topicEntity;

    public ReadMessageQueueServerUtile() {
    }
    public ReadMessageQueueServerUtile(String filePath, TopicEntity topicEntity, int baseSetoff, int index) {
        this.topicEntity = topicEntity;
        this.filePath = filePath;
        // TODO yingdang duqusuoyinwenjian queding index
        this.index = index;
        this.baseSetoff = baseSetoff;
        getMmp(index);

    }

    public void setIndex(int indes){
        this.index = indes;
    }

    private void putFileMessage(){
        try {

            File file = new File(filePath + topicEntity.topic + baseSetoff + ".log");
            randomAccessFile = new RandomAccessFile(file,"rw");
            targeFileChannel = randomAccessFile.getChannel();
            mappedByteBuffer = targeFileChannel.map(FileChannel.MapMode.READ_WRITE,0,defSize);
            this.setIndex(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileMessageEntity fileRead( ) {


//        byte[] msgBytes = messages.getBytes("utf-8");
//        byte[] lenghtBts = InttoByteArray(lenght);
//        byte[] setoffBts = InttoByteArray(setoff);
        byte[] lenghtBts = new byte[4];
        mappedByteBuffer.get(lenghtBts, 0, 4);
        byte[] setoffBts = new byte[4];
        mappedByteBuffer.get(setoffBts, 0, 4);
        int lenght = ByteArraytoInt(lenghtBts);
        byte[] msgBytes = new byte[lenght];
        mappedByteBuffer.get(msgBytes, 0, lenght);
        index = index+lenghtBts.length+setoffBts.length+msgBytes.length;
        int setoff = ByteArraytoInt(setoffBts);
        if (index == defSize) {
            this.baseSetoff = setoff;
            putFileMessage();
        }
        return new FileMessageEntity(new String(msgBytes),setoff);

    }

    public void initReadQueue( int offSet) {


//        byte[] msgBytes = messages.getBytes("utf-8");
//        byte[] lenghtBts = InttoByteArray(lenght);
//        byte[] setoffBts = InttoByteArray(setoff);
        while (true) {
            byte[] lenghtBts = new byte[4];
            mappedByteBuffer.get(lenghtBts, 0, 4);
            byte[] setoffBts = new byte[4];
            mappedByteBuffer.get(setoffBts, 0, 4);
            int lenght = ByteArraytoInt(lenghtBts);
            byte[] msgBytes = new byte[lenght];
            mappedByteBuffer.get(msgBytes, 0, lenght);
            index = index + lenghtBts.length + setoffBts.length + msgBytes.length;
            int setoff = ByteArraytoInt(setoffBts);
            if (setoff >= (offSet - 1)) {
                return;
            }
        }

    }


    public void getMmp(int index){
        try {

            File file = new File(filePath + topicEntity.topic + baseSetoff + ".log");
            randomAccessFile = new RandomAccessFile(file,"rw");
            targeFileChannel = randomAccessFile.getChannel();
            mappedByteBuffer = targeFileChannel.map(FileChannel.MapMode.READ_WRITE,index,defSize-index);
            this.setIndex(index);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
