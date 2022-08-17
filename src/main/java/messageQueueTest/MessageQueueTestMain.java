package messageQueueTest;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class MessageQueueTestMain {

    public static void main(String... args){
        try {

            Socket socket = new Socket("localhost", 9099);

            OutputStream os = socket.getOutputStream();
            PrintStream printStream = new PrintStream(os);
            printStream.println("放置消息");
            printStream.flush();
            log.info("关闭消息队列请输入：exit");
            while (true) {
                Scanner sc = new Scanner(System.in);
                String message = sc.nextLine();
                printStream.println(message);
                printStream.flush();
                if ("exit".equals(message)) {
                    log.info("正在关闭消息队列！");
                    break;
                }
            }

//            for (int i = 0; i < 1000000 ; i ++) {
////                    messageQueueServer.putMessage(i + " ");
//                printStream.println(i + " ");
//                printStream.flush();
//            }

//            log.info("关闭消息队列请输入：exiteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            printStream.close();
            os.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
