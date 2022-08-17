package messageQueue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static messageQueue.FileMessageEntity.ByteArraytoInt;

public class FileStaticUtil {
    static String  path = "C:\\test\\create\\";
    /**
     * get StringList File
     */
    public static Boolean getFile(String mytopic) throws IOException, ClassNotFoundException {
        List<List<MessageEntity>> retList = new ArrayList<>();
        File file = new File(path+mytopic+"\\");
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public static int getMaxSetOff(String mytopic) throws IOException, ClassNotFoundException {
        List<List<MessageEntity>> retList = new ArrayList<>();
        File file = new File(path+mytopic+"\\");
        if (!file.exists()) {
            return 0;
        }
        String maxFileName = "0";
        File[] files = file.listFiles();
        for (File file1 : files){
            String filename = file1.getName();
            String[] filenames = filename.split("\\.");
            if (filenames[0].compareTo(maxFileName)>0){
                maxFileName = filenames[0];
            }
        }
        File indexfile = new File(path+mytopic+"\\"+maxFileName+".ind");
        RandomAccessFile randomAccessFile = new RandomAccessFile(indexfile,"r");
        FileChannel targeFileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = targeFileChannel.map(FileChannel.MapMode.READ_ONLY,0,10*1024*1024);
        int maxsetoff = 0;
        int lastPosition = 0;
        for (int i = 0;i<((10*1024*1024)/8);i++){
            byte[] setoffBts = new byte[4];
            mappedByteBuffer.get(setoffBts, 0, 4);
            int setof = ByteArraytoInt(setoffBts);
            byte[] indexpartitionBts = new byte[4];
            mappedByteBuffer.get(indexpartitionBts, 0, 4);
            int indexpartition = ByteArraytoInt(indexpartitionBts);
            if (setof > maxsetoff){
                maxsetoff = setof;
                lastPosition = indexpartition;
            }
        }
        targeFileChannel.close();
        randomAccessFile.close();
        File logfile = new File(path+mytopic+"\\"+maxFileName+".log");
        RandomAccessFile lograndomAccessFile = new RandomAccessFile(logfile,"r");
        FileChannel logtargeFileChannel = lograndomAccessFile.getChannel();
        MappedByteBuffer logmappedByteBuffer = logtargeFileChannel.map(FileChannel.MapMode.READ_ONLY,lastPosition,1024*1024*1024-lastPosition);


        while (true) {
            byte[] lenghtBts = new byte[4];
            logmappedByteBuffer.get(lenghtBts, 0, 4);
            byte[] setoffBts = new byte[4];
            logmappedByteBuffer.get(setoffBts, 0, 4);
            int lenght = ByteArraytoInt(lenghtBts);
            byte[] msgBytes = new byte[lenght];
            logmappedByteBuffer.get(msgBytes, 0, lenght);
            int setoff = ByteArraytoInt(setoffBts);
            if (setoff > maxsetoff) {
                maxsetoff = setoff;
            }else {
                break;
            }
        }
        logtargeFileChannel.close();
        lograndomAccessFile.close();
        return maxsetoff;

    }
}
