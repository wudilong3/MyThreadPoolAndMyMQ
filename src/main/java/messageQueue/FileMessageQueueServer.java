package messageQueue;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息队列实体类
 */
@Slf4j
public class FileMessageQueueServer {
    long partition = 0L;
    //消息列表
    public  String topic = "topic";
    List<MessageEntity> stringList = new ArrayList<>();
    List<MessageEntity> bufferStringList = new ArrayList<>();
    Integer maxSize = 100000;
    public String  path = "C:\\test\\create\\";
    public TopicEntity topicEntity = new TopicEntity();
    MessageQueueServerUtile messageQueueServerUtile;
    ReadMessageQueueServerUtile readMessageQueueServerUtile;
    FileIndexReadUtile fileIndexReadUtile;
    int readFlage = 0;
    int offset = 0;
//    Map indexMap = new HashMap();

    public FileMessageQueueServer(String topic,int offset){
        this.offset = offset;
        this.topicEntity.maxOffSet = offset;
        this.topicEntity.topic = topic;
        this.path = this.path + topic+"\\";
        //TODO 应当根据文件最新往后追加数据
        messageQueueServerUtile = new MessageQueueServerUtile(path,topicEntity,0,0);
    }

    public FileMessageQueueServer getReadUtil(int offset) throws Exception {
        if (offset>topicEntity.maxOffSet){
            throw new Exception("偏移量大于最大偏移量");
        } else {
            readFlage =1;
        }
        fileIndexReadUtile = new FileIndexReadUtile(path,topicEntity,0);
        //TODO 这里目前是循环查找，为性能优化应当做查询算法优化
        int positition = fileIndexReadUtile.getPosition(offset);
        readMessageQueueServerUtile = new ReadMessageQueueServerUtile(path,topicEntity,0,positition);
        readMessageQueueServerUtile.initReadQueue(offset);
        return this;
    }


    /**
     * put method
     */
    public synchronized Boolean  putMessage(String message) throws UnsupportedEncodingException {
//        while (stringList.size()>maxSize) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        MessageEntity messageEntity = new MessageEntity();
//        messageEntity.messages = (topic+message).toCharArray();
//        messageEntity.partition = partition++;
        FileMessageEntity fileMessageEntity = new FileMessageEntity(message,topicEntity.maxOffSet+1);
        long position = messageQueueServerUtile.myfileWrite(fileMessageEntity);
        topicEntity.maxOffSet +=1;
        readFlage =1;
        notify();
        return true;
    }


//    public void putUpMessage(String message) {
//        MessageEntity messageEntity = new MessageEntity();
//        messageEntity.messages = (topic+message).toCharArray();
//        messageEntity.partition = partition++;
////        putMessage(messageEntity);
//        if (bufferStringList.size()>1000) {
//            try {
//                log.info("create file begin");
//                createFile();
//                log.info("create file end");
//            } catch (IOException e) {
//                e.printStackTrace();
//                log.error("create file filed !!!!!");
//            }
//        } else {
//            bufferStringList.add(messageEntity);
//        }
//    }

    /**
     * get method
     */
    public synchronized String getMessage() {
//        while (fileMessageEntity.setoff == topicEntity.maxOffSet) {
        while (0==readFlage) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        FileMessageEntity fileMessageEntity = readMessageQueueServerUtile.fileRead();
        if (fileMessageEntity.setoff == topicEntity.maxOffSet){
//            log.info("========================================");
            readFlage = 0;
        }
        return fileMessageEntity.messages;
    }

    /**
     * get method partiton
     */
    public synchronized String getMessage(long pqrtition) {
        while (stringList.size() == 0 || stringList.get(0).partition < pqrtition) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
        log.info("get message from message queue success");
        return new String(stringList.remove(0).messages);
    }

    /**
     * create StringList File
     */
    private void createFile() throws IOException {
        File file = new File(path+topic+"\\");
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = path+topic+"\\"+ partition + ".dat";
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
        objectOutputStream.writeObject(bufferStringList);
//        indexMap.put(partition,fileName);
//        String indexPath = path+topic+"\\index.dat";
//        ObjectOutputStream indexOutputStream = new ObjectOutputStream(new FileOutputStream(indexPath));
//        indexOutputStream.writeObject(indexMap);
        bufferStringList.clear();
    }

    /**
     * get StringList File
     */
    public List<List<MessageEntity>> getFile(long partition) throws IOException, ClassNotFoundException {
        List<List<MessageEntity>> retList = new ArrayList<>();
        File file = new File(path+topic+"\\");
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] files = file.listFiles();
        if (files.length == 0) return null;
//        ObjectInputStream objectIndexInputStream = new ObjectInputStream(new FileInputStream(new File(path+topic+"\\index.dat")));
//        Map fileIndexMap = (Map) objectIndexInputStream.readObject();
//        this.indexMap = fileIndexMap;
        for (File file1 : files) {
//            String sdd = file1.getName();
//            String[] sss = sdd.split("\\.");
//            String adfas = sss[0];
            if (Integer.parseInt(file1.getName().split("\\.")[0]) >= partition){
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file1));
                List<MessageEntity> fileList = (List<MessageEntity>) objectInputStream.readObject();
                retList.add(fileList);
            }
        }
        return retList;
    }

    /**
     * get StringList File
     */
    public Boolean getFile(String mytopic) throws IOException, ClassNotFoundException {
        List<List<MessageEntity>> retList = new ArrayList<>();
        File file = new File(path+mytopic+"\\");
        if (!file.exists()) {
            return false;
        }
        return true;
    }
}
