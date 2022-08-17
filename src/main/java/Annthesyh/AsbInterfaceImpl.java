package Annthesyh;

import Annthesyh.TestDire.Csumer;

import java.lang.reflect.Method;

//获取注解内容实现类
public class AsbInterfaceImpl implements AsbInterface {
    @Override
    public String[] getvTopicList(Class<?> c) {
        Csumer annotation = c.getAnnotation(Csumer.class);
        return annotation.Topic();
    }

    @Override
    public String[] getvMethodTopicList(Method method) {
        Csumer annotation = method.getAnnotation(Csumer.class);
        return annotation.Topic();
    }


    @Override
    public String[] getvMethodPartitionList(Method method){

        Csumer annotation = method.getAnnotation(Csumer.class);
        return annotation.Partition();
    }
}
