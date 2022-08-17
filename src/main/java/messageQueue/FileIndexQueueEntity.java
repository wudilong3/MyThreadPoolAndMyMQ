package messageQueue;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import static messageQueue.FileMessageEntity.InttoByteArray;

public class FileIndexQueueEntity implements Serializable {

    private static final long serialVersionUID = -4586234214654667710L;

    public  int baseSetoff;
    public int maxPosition;
    public int masSetoff;

    public FileIndexQueueEntity(int baseSetoff, int maxPosition, int masSetoff ){
        this.baseSetoff = baseSetoff;
        this.maxPosition = maxPosition;
        this.masSetoff = masSetoff;
    }

}
