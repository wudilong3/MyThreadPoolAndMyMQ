package messageQueue;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class ServerThread extends Thread {
    //socket实例
    private Socket socket = null;
    //消息队列服务实例
    private MessageQueueServer messageQueueServer = null;

    //有参构造方法
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            OutputStream os = socket.getOutputStream();
            PrintStream printStream = new PrintStream(os);

            String info = null;
            info = br.readLine();
            //判断客户端是消费者还是生产者
            if ("放置消息".equals(info)){
                //启动生产者线程
                new PutServerThread(br).start();
            }else {
                //启动消费者线程
                new GetServerThread(printStream,br).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
