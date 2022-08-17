package messageQueue;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileAddMessageThread extends Thread {
    private FileMessageQueueServer fileMessageQueueServer;
    public FileAddMessageThread(FileMessageQueueServer fileMessageQueueServer) {
        this.fileMessageQueueServer = fileMessageQueueServer;
    }
    @SneakyThrows
    @Override
    public void run() {
        for (int i = 0; i < 1000 ; i ++) {
            fileMessageQueueServer.putMessage(i + "qweqeqwqw ");
        }
    }
}
