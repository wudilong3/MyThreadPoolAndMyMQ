package Annthesyh;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PutMessageText {

    private static MessageQueueLocal messageQueueLocal = MessageQueueLocal.getMessageQueueLocal();

    public void putMessage(String message){
        log.info("client send message : {}", message);
        //将消息放入消息队列实例
        messageQueueLocal.putMessage(message);
    }
}
