package messageQueue;

import java.io.Serializable;

public class TopicEntity implements Serializable {

    private static final long serialVersionUID = -6456987234456567710L;

    public String topic;

    public int maxOffSet;
}
