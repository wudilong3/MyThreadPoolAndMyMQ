package messageQueue;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import static messageQueue.FileMessageEntity.InttoByteArray;

public class FileIndexEntity implements Serializable {

    private static final long serialVersionUID = -4586663545654667710L;

//    public String messages;

//    public long partition;

    public int indexpartition;

    public int setoff;

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] indexpartitionBts = InttoByteArray(indexpartition);
        byte[] setoffBts = InttoByteArray(setoff);
        byte[] allMsgBts = new byte[indexpartitionBts.length+setoffBts.length];
        System.arraycopy(setoffBts,0,allMsgBts, 0,setoffBts.length);
        System.arraycopy(indexpartitionBts,0,allMsgBts,setoffBts.length,indexpartitionBts.length);
        return allMsgBts;
    }

    public FileIndexEntity(int setoff,int indexpartition ){
        this.indexpartition = indexpartition;
        this.setoff = setoff;
    }

}
