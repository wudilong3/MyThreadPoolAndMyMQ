package messageQueue;

import Annthesyh.MessageQueueLocal;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class AddFileMessageThread extends Thread {
    private MessageQueueServer messageQueueServer;
    private long partition;
    private String key;
    public AddFileMessageThread(MessageQueueServer messageQueueServer,String key,long partition) {
        this.key = key;
        this.partition = partition;
        this.messageQueueServer = messageQueueServer;
    }
    @Override
    public void run() {
        try {

            List<List<MessageEntity>> strlili = messageQueueServer.getFile(partition);
            if (strlili == null) {
                return;
            }
            for (List<MessageEntity> strli : strlili) {
                for (MessageEntity str : strli){
                    if (str.partition>=partition) {
                        messageQueueServer.putMessage(str);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
