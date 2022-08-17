package Annthesyh;

import java.lang.reflect.Method;

//获取注解内容接口类
public interface AsbInterface {
    public String[] getvTopicList(Class<?> c);
    public String[] getvMethodTopicList(Method method);
    public String[] getvMethodPartitionList(Method method);
}
