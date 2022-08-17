package Annthesyh;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息队列实体类
 */
@Slf4j
public class MessageQueueLocal {
    //消息列表
    List<String> stringList = new ArrayList<>();
    Integer maxSize = 100000;

    private static MessageQueueLocal messageQueueLocal = null;

    private MessageQueueLocal(){

    }

    //懒汉模式
    public static MessageQueueLocal getMessageQueueLocal(){
        if (messageQueueLocal == null) {
            synchronized (MessageQueueLocal.class){
                if (messageQueueLocal == null){
                    messageQueueLocal = new MessageQueueLocal();
                }
            }
        }
        return messageQueueLocal;
    }

    /**
     * put method
     */
    public synchronized void  putMessage(String message) {
        while (stringList.size()>maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stringList.add(message);
        notify();
//        log.info("add message to message queue success");
    }

    /**
     * get method
     */
    public synchronized String getMessage() {
        while (stringList.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
//        log.info("get message from message queue success");
        return stringList.remove(0);
    }
}
