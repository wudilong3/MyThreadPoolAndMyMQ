package messageQueue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileGetMessageThread extends  Thread {
    private FileMessageQueueServer fileMessageQueueServer;
    public FileGetMessageThread(FileMessageQueueServer fileMessageQueueServer,int setOff) throws Exception {
        this.fileMessageQueueServer = fileMessageQueueServer;
        fileMessageQueueServer.getReadUtil(setOff);
    }
    @Override
    public void run() {
        while (true) {
            String message = fileMessageQueueServer.getMessage();
            log.info("get message : " + message);
        }
    }
}
