package messageQueue;

import java.io.Serializable;

public class MessageEntity implements Serializable {

    private static final long serialVersionUID = -6456451245654667710L;

    public char[] messages;

    public long partition;
}
