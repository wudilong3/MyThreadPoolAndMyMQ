package messageQueue;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息队列启动主类
 * 开启消息队列服务端口
 * 启动生产者线程或消费者线程
 */

@Slf4j
public class MainTest {
    public static List<MessageQueueServer> messageQueueServerList = new ArrayList<>();
    public static Map<String,FileMessageQueueServer> fileMessageQueueServerMap = new HashMap<>();
    public static void main(String... args) throws Exception {

//        log.info("========================================");
//        FileMessageQueueServer fileMessageQueueServer = new FileMessageQueueServer("topicc",0);
////        FileAddMessageThread fileAddMessageThread = new FileAddMessageThread(fileMessageQueueServer);
////        fileAddMessageThread.start();
////        Thread.sleep(500);
//        FileGetMessageThread fileGetMessageThread = new FileGetMessageThread(fileMessageQueueServer, 0);
//        fileGetMessageThread.start();
//        MessageQueueServer messageQueueServer = new MessageQueueServer();
//        messageQueueServerList.add(messageQueueServer);
//        AddFileMessageThread addFileMessageThread = new AddFileMessageThread(messageQueueServer);
//        addFileMessageThread.start();
//        socketSta();
        fileSocketSta();
//        FileMessageQueueServer fileMessageQueueServer = new FileMessageQueueServer("topicc",FileStaticUtil.getMaxSetOff("topicc"));
//        FileGetMessageThread fileGetMessageThread = new FileGetMessageThread(fileMessageQueueServer, 100);
//        fileGetMessageThread.start();
//        TopicEntity topicEntity = new TopicEntity();
//        topicEntity.maxOffSet = 1000;
//        topicEntity.topic = "topicc";
//        ReadMessageQueueServerUtile readMessageQueueServerUtile = new ReadMessageQueueServerUtile("C:\\test\\create\\topicc\\",topicEntity,0,0);
//        log.info(readMessageQueueServerUtile.fileRead().messages);;
    }
    private static void socketSta() {
        try {

            //开启服务端口
            ServerSocket serverSocket = new ServerSocket(9099);

            Socket socket = null;

            while (true) {
                //接收到客户端的请求
                socket = serverSocket.accept();
                //启动socket服务线程
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
                //获取客户端IP地址
                InetAddress address = socket.getInetAddress();
                log.info("client ip is {}", address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileSocketSta() {
        try {

            //开启服务端口
            ServerSocket serverSocket = new ServerSocket(9099);

            Socket socket = null;

            while (true) {
                //接收到客户端的请求
                socket = serverSocket.accept();
                //启动socket服务线程
                FileServerThread fileServerThread = new FileServerThread(socket);
                fileServerThread.start();
                //获取客户端IP地址
                InetAddress address = socket.getInetAddress();
                log.info("client ip is {}", address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
