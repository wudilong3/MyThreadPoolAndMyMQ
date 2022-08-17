package messageQueue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddMessageThread extends Thread {
    private MessageQueueServer messageQueueServer;
    public AddMessageThread(MessageQueueServer messageQueueServer) {
        this.messageQueueServer = messageQueueServer;
    }
    @Override
    public void run() {
        for (int i = 0; i < 1000 ; i ++) {
            messageQueueServer.putUpMessage(i + " ");
        }
    }
}
