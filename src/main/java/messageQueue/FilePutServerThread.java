package messageQueue;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

/**
 * 生产者线程实体类
 */
@Slf4j
public class FilePutServerThread extends Thread {
    //socket实例
    private Socket socket = null;
    private BufferedReader br = null;
    //文件消息队列服务实例
//    private FileMessageQueueServer fileMessageQueueServer = null;
    //有参构造方法
    public FilePutServerThread(BufferedReader br) {
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

//            String topic =  br.readLine();
//            fileMessageQueueServer = MainTest.fileMessageQueueServerMap.get(topic);
//
//            if (this.fileMessageQueueServer == null){
//                fileMessageQueueServer = new FileMessageQueueServer(topic,0);
//                MainTest.fileMessageQueueServerMap.put(topic,fileMessageQueueServer);
//            }
            String info = null;

            while ((info = br.readLine())!=null) {
//                log.info("client send message : {}", info);
                String[] infos = info.split(":");
                FileMessageQueueServer fileMessageQueueServer = MainTest.fileMessageQueueServerMap.get(infos[0]);
                if (fileMessageQueueServer == null){
                    fileMessageQueueServer = new FileMessageQueueServer(infos[0],0);
                    MainTest.fileMessageQueueServerMap.put(infos[0],fileMessageQueueServer);
                }
                //将消息放入消息队列实例
                fileMessageQueueServer.putMessage(infos[1]);
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
