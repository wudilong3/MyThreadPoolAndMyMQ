package messageQueueTest;

import Annthesyh.AnnTestC;
import Annthesyh.AsbInterface;
import Annthesyh.AsbInterfaceImpl;
import Annthesyh.ExcueMethod;
import Annthesyh.TestDire.Csumer;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

@Slf4j
public class GetMessageQueueTest {
    public static void main(String... args){
        try {

            AsbInterface asbInterface = new AsbInterfaceImpl();
            Socket socket = new Socket("localhost", 9099);
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            OutputStream os = null;
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            os = socket.getOutputStream();
            PrintStream printStream = new PrintStream(os);
            printStream.println("获得消息");
            printStream.flush();
            Map methList = new ExcueMethod<AnnTestC,Csumer>().getMethod(AnnTestC.class,
                    Csumer.class, asbInterface);
            printStream.println(
                    JSON.toJSONString(methList));
            printStream.flush();
            String info = null;
            while ((info = br.readLine()) != null) {
                log.info("get message from message queue  : {}", info);
                new ExcueMethod<AnnTestC,Csumer>().parseMethod(AnnTestC.class,
                        Csumer.class, asbInterface,
                        "ccdv",
                        info);
                if ("exit".equals(info)) {
                    log.info("消息队列关闭完成！");
                    break;
                }
            }
            os.close();
            br.close();
            isr.close();
            is.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
