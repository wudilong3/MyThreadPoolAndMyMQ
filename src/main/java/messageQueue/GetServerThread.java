package messageQueue;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class GetServerThread extends Thread {


    private  long partition ;

    private BufferedReader br = null;
    private Socket socket = null;
    private PrintStream printStream = null;
    private MessageQueueServer messageQueueServer = null;
    public GetServerThread(PrintStream printStream,BufferedReader br) {
        this.printStream = printStream;
        this.br = br;
    }

    @Override
    public void run() {
        try {
            JSONObject jso = JSONObject.parseObject(br.readLine());
            Map<String,String> jsoMap = new HashMap<>();
            jsoMap.putAll((Map)jso);
            Set<String> setKey = jsoMap.keySet();
            for (String key : setKey){
                this.messageQueueServer = null;
                String[] keys = key.split("\\[");
                keys = keys[1].split("\\]");
                String finalKey = keys[0];
                for (MessageQueueServer messageQueueServer1 : MainTest.messageQueueServerList) {
                    if (finalKey.equals(messageQueueServer1.topic)){
                        this.messageQueueServer = messageQueueServer1;
                        break;
                    }
                }
                if (this.messageQueueServer == null){
                    if (MessageQueueServer.getFile(finalKey)){
                        this.messageQueueServer = new MessageQueueServer();
                        messageQueueServer.topic = finalKey;
                        MainTest.messageQueueServerList.add(this.messageQueueServer);
                    }else{
                        return;
                    }
                }
                AddFileMessageThread addFileMessageThread = new AddFileMessageThread(this.messageQueueServer,finalKey,Long.parseLong(jso.get(key).toString()));
                addFileMessageThread.start();
                for (MessageQueueServer messageQueueServer1 : MainTest.messageQueueServerList) {
                    if (finalKey.contains(messageQueueServer1.topic)) {
                        new Thread(() -> {
                            while (true) {
                                String message = messageQueueServer1.getMessage(Long.parseLong(jso.get(key).toString()));
                                log.info("get message : " + message);
                                printStream.println(message);
                                printStream.flush();
                                if ("exit".equals(message)) {
                                    log.info("正在关闭消息队列！");
                                    break;
                                }
                            }
                        }).start();
                    }
                }
            }



//            printStream.close();
////            os.close();
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
