package Annthesyh;

import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

@Slf4j
public class MessageQueueTestLocalMain extends Thread {

    @Override
    public void run(){
        initMessageQueueTestLocalMain();
    }

    public void initMessageQueueTestLocalMain(){
        try {

            Socket socket = new Socket("localhost", 9099);

            OutputStream os = socket.getOutputStream();
            PrintStream printStream = new PrintStream(os);
            printStream.println("放置消息");
            printStream.flush();
//            printStream.println("ccdv");
//            printStream.flush();
            log.info("关闭消息队列请输入：exit");
            MessageQueueLocal messageQueueLocal = MessageQueueLocal.getMessageQueueLocal();
            while (true) {
                String message = messageQueueLocal.getMessage();
                log.warn("get message : " + message);
                printStream.println(message);
                printStream.flush();
                if ("exit".equals(message)) {
                    log.info("正在关闭消息队列！");
                    break;
                }
            }
            printStream.close();
            os.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
