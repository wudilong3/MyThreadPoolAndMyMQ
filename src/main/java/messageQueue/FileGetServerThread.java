package messageQueue;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class FileGetServerThread extends Thread {


    private  long partition ;

    private BufferedReader br = null;
    private Socket socket = null;
    private PrintStream printStream = null;
//    private FileMessageQueueServer fileMessageQueueServer = null;
    public FileGetServerThread(PrintStream printStream, BufferedReader br) {
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
                FileMessageQueueServer fileMessageQueueServer = null;
                String[] keys = key.split("\\[");
                keys = keys[1].split("\\]");
                String finalKey = keys[0];

                fileMessageQueueServer = MainTest.fileMessageQueueServerMap.get(finalKey);
                if (fileMessageQueueServer == null){
                    if (FileStaticUtil.getFile(finalKey)){
                        fileMessageQueueServer = new FileMessageQueueServer(finalKey,FileStaticUtil.getMaxSetOff(finalKey));
                        MainTest.fileMessageQueueServerMap.put(finalKey,fileMessageQueueServer);
                    }else{
                        continue;
                    }
                }

                fileMessageQueueServer.getReadUtil(Integer.parseInt(jsoMap.get(key)));
                FileMessageQueueServer finalFileMessageQueueServer = fileMessageQueueServer;
                new Thread(() -> {
                    while (true) {
                        String message = finalKey+ ":" + finalFileMessageQueueServer.getMessage();
//                        log.info("get message : " + message);
                        printStream.println(message);
                        printStream.flush();
                        if ("exit".equals(message)) {
                            log.info("正在关闭消息队列！");
                            break;
                        }
                    }
                }).start();
//                AddFileMessageThread addFileMessageThread = new AddFileMessageThread(this.messageQueueServer,finalKey,Long.parseLong(jso.get(key).toString()));
//                addFileMessageThread.start();

            }



//            printStream.close();
////            os.close();
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
