package Annthesyh;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddMessageLocalThread extends Thread {
    private MessageQueueLocal messageQueueLocal = MessageQueueLocal.getMessageQueueLocal();
    private String topic = "ccdv";
    @Override
    public void run() {
        for (int i = 0; i < 100000 ; i ++) {
            messageQueueLocal.putMessage(topic+":"+i + " ");
            messageQueueLocal.putMessage("tty:"+i + "tty ");
            messageQueueLocal.putMessage("sdf:"+i + "sdf ");
        }
    }
}
