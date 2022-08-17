package messageQueue;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息队列启动主类
 * 开启消息队列服务端口
 * 启动生产者线程或消费者线程
 */

@Slf4j
public class MainReadTest {
    public static List<MessageQueueServer> messageQueueServerList = new ArrayList<>();
    public static void main(String... args) throws Exception {
        FileMessageQueueServer fileMessageQueueServer = new FileMessageQueueServer("topicc",100);
        FileGetMessageThread fileGetMessageThread = new FileGetMessageThread(fileMessageQueueServer,100);
        fileGetMessageThread.start();
//        MessageQueueServer messageQueueServer = new MessageQueueServer();
//        messageQueueServerList.add(messageQueueServer);
//        AddFileMessageThread addFileMessageThread = new AddFileMessageThread(messageQueueServer);
//        addFileMessageThread.start();
//        socketSta();
    }
}
