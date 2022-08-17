package messageQueue;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * 生产者线程实体类
 */
@Slf4j
public class PutServerThread extends Thread {
    //socket实例
    private Socket socket = null;
    private BufferedReader br = null;
    //消息队列服务实例
    private MessageQueueServer messageQueueServer = null;
    //有参构造方法
    public PutServerThread(BufferedReader br) {
        this.br = br;
    }

    @Override
    public void run() {
//        InputStream is = null;
//        InputStreamReader isr = null;
//        BufferedReader br = null;
        try {
//            is = socket.getInputStream();
//            isr = new InputStreamReader(is);
//            br = new BufferedReader(isr);

            String topic =  br.readLine();
            for (MessageQueueServer messageQueueServer1 : MainTest.messageQueueServerList) {
                if (topic.equals(messageQueueServer1.topic)){
                    this.messageQueueServer = messageQueueServer1;
                    break;
                }
            }
            if (this.messageQueueServer == null){
                this.messageQueueServer = new MessageQueueServer();
                messageQueueServer.topic=topic;
                MainTest.messageQueueServerList.add(this.messageQueueServer);
            }
            String info = null;

            while ((info = br.readLine())!=null) {
                log.info("client send message : {}", info);
                //将消息放入消息队列实例
                messageQueueServer.putUpMessage(info);
                if ("exit".equals(info)) {
                    log.info("正在关闭消息队列！");
                    break;
                }
            }

            socket.shutdownInput();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
