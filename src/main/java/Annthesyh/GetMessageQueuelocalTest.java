package Annthesyh;

import Annthesyh.TestDire.Csumer;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Map;

@Slf4j
public class GetMessageQueuelocalTest extends Thread {

    @Override
    public void run () {
        initGetMessageQueuelocalTest();
    }
    public static void initGetMessageQueuelocalTest(){
        try {
            AsbInterface asbInterface = new AsbInterfaceImpl();
            Socket socket = new Socket("localhost", 9099);
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            OutputStream os = null;
            os = socket.getOutputStream();
            PrintStream printStream = new PrintStream(os);
            printStream.println("获得消息");
            printStream.flush();
            Map methList = new ExcueMethod<AnnTestC,Csumer>().getMethod(AnnTestC.class,
                    Csumer.class, asbInterface);
            new ExcueMethod<AnnTestC,Csumer>().getExuMethod(AnnTestC.class,
                    Csumer.class, asbInterface,methList);
            printStream.println(
                    JSON.toJSONString(methList));
            printStream.println(methList.toString());
            printStream.flush();
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
//                log.info("get message from message queue  : {}", info);
                String[] infos = info.split(":");
                //TODO 初始化时将方法对象获取到，在这里只执行方法，不用在每次都遍历执行方法
//                new ExcueMethod<AnnTestC,Csumer>().parseMethod(AnnTestC.class,
//                        Csumer.class, asbInterface,
//                        infos[0],
//                        infos[1]);
                AnnrheTest.fsmetObjHashMap.get(infos[0]).method.invoke(AnnrheTest.fsmetObjHashMap.get(infos[0]).object,infos[1]);
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
