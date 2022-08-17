package messageQueue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetMessageThread extends  Thread {
    private MessageQueueServer messageQueueServer;
    public GetMessageThread(MessageQueueServer messageQueueServer) {
        this.messageQueueServer = messageQueueServer;
    }
    @Override
    public void run() {
        for (int i = 0; i < 1000 ; i ++) {
            String message = messageQueueServer.getMessage();
            log.info("get message : " + message);
        }
    }
}
