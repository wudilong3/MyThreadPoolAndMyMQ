package messageQueue;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class FileMessageEntity implements Serializable {

    private static final long serialVersionUID = -4586663545654667710L;

    public String messages;

    public long partition;

    public int lenght;

    public int setoff;

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] msgBytes = messages.getBytes("utf-8");
        lenght = msgBytes.length;
        byte[] lenghtBts = InttoByteArray(lenght);
        byte[] setoffBts = InttoByteArray(setoff);
        byte[] allMsgBts = new byte[lenghtBts.length+setoffBts.length+msgBytes.length];
        System.arraycopy(lenghtBts,0,allMsgBts,0,lenghtBts.length);
        System.arraycopy(setoffBts,0,allMsgBts,lenghtBts.length,setoffBts.length);
        System.arraycopy(msgBytes,0,allMsgBts,lenghtBts.length+setoffBts.length,msgBytes.length);
        return allMsgBts;
    }

    public FileMessageEntity (String messages,int setoff){
        this.messages = messages;
        this.setoff = setoff;
    }

    public static byte[] InttoByteArray(int n){
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }
    public static int ByteArraytoInt(byte[] bArr){
        if (bArr.length!=4){
            return -1;
        }
        return (int)((((bArr[3] & 0xff) << 24)
                | ((bArr[2] & 0xff) << 16)
                | ((bArr[1] & 0xff) << 8)
                | ((bArr[0] & 0xff) << 0)));
    }
}
