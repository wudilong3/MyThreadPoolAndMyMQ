package Annthesyh;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static Annthesyh.AnnrheTest.fsmetObjHashMap;

public class ExcueMethod<T,E extends Annotation> {

    /**
     * //执行接口实现类的带有注解的方法
     * @param clazz 消费者接口类
     * @param annotationClass 消费者注解类
     * @param asbInterface 获取注解内容接口类
     * @param name 方法上的
     * @param prame
     * @throws Exception
     */

    public void parseMethod(final Class<T> clazz, final Class<E> annotationClass, AsbInterface asbInterface, String name, Object prame) throws Exception {
        //是否类注解
        boolean slaFlg = true;


        Set<Class<?>> classlers = new HashSet<>();

        //判断是否是接口类
        if (clazz.isInterface()){
            String packageName = clazz.getPackage().getName();
            Set<Class<?>> clsList =new Scanner().getClasses(packageName);
            if (clsList != null && clsList.size() > 0) {
                for (Class<?> cls : clsList) {
                    if (clazz.isAssignableFrom(cls)) {
                        if (!clazz.equals(cls)){
                            classlers.add(cls);
                        }
                    }
                }
            }
        }
        if ( classlers.size() > 0) {
            for (Class<?> cls : classlers) {
                if (slaFlg) {
                    if (cls.getAnnotation(annotationClass) == null) {
                        continue;
                    }
                }
//                String[] vlueList = asbInterface.getvVlueList(cls);
                exuMethod(cls,prame,asbInterface,name);
            }
        }
    }
    //执行方法
    public void exuMethod(Class<?> cls,Object prame,AsbInterface asbInterface,
                          String name) throws Exception {
        final Object object = cls.getConstructor(new Class[] {}).newInstance();
        final Method[] methods = cls.getDeclaredMethods();
        for (final Method method : methods) {
            if (null != prame) {
                if (Arrays.toString(asbInterface.getvMethodTopicList(method)).contains(name))
                    method.invoke(object,prame);
            }
        }
    }



    /**
     * //获取系统中所有的topic
     * @param clazz 消费者接口类
     * @param annotationClass 消费者注解类
     * @param asbInterface 获取注解内容接口类
     * @throws Exception
     */

    public Map getMethod(final Class<T> clazz, final Class<E> annotationClass, AsbInterface asbInterface) throws Exception {
        //是否类注解
        boolean slaFlg = true;

        //判断是否是接口类
        Map retList = new HashMap();
        if (clazz.isInterface()){
            String packageName = clazz.getPackage().getName();
            Set<Class<?>> clsList =new Scanner().getClasses(packageName);
            if (clsList != null && clsList.size() > 0) {
                for (Class<?> cls : clsList) {
                    if (clazz.isAssignableFrom(cls)) {
                        if (!clazz.equals(cls)){
                            if (slaFlg) {
                                if (cls.getAnnotation(annotationClass) == null) {
                                    continue;
                                }
                            }
                            retList = getMethod(cls,asbInterface);
                        }
                    }
                }
            }
        }
        return retList;
    }

    //获取方法上的注解值
    public Map getMethod(Class<?> cls, AsbInterface asbInterface) throws Exception {
        Map methStrList = new HashMap();
        final Object object = cls.getConstructor(new Class[] {}).newInstance();
        final Method[] methods = cls.getDeclaredMethods();
        for (final Method method : methods) {
            methStrList.put(Arrays.toString(asbInterface.getvMethodTopicList(method)),asbInterface.getvMethodPartitionList(method)[0]);
        }
        methStrList.remove("[]");
        return methStrList;
    }















    /**
     * //获取相应注解的方法
     * @param clazz 消费者接口类
     * @param annotationClass 消费者注解类
     * @param asbInterface 获取注解内容接口类
     * @throws Exception
     */

    public void getExuMethod(final Class<T> clazz, final Class<E> annotationClass, AsbInterface asbInterface, Map namMap) throws Exception {
        //是否类注解
        boolean slaFlg = true;


        Set<Class<?>> classlers = new HashSet<>();

        //判断是否是接口类
        if (clazz.isInterface()){
            String packageName = clazz.getPackage().getName();
            Set<Class<?>> clsList =new Scanner().getClasses(packageName);
            if (clsList != null && clsList.size() > 0) {
                for (Class<?> cls : clsList) {
                    if (clazz.isAssignableFrom(cls)) {
                        if (!clazz.equals(cls)){
                            classlers.add(cls);
                        }
                    }
                }
            }
        }
        if ( classlers.size() > 0) {
            for (Class<?> cls : classlers) {
                if (slaFlg) {
                    if (cls.getAnnotation(annotationClass) == null) {
                        continue;
                    }
                }
//                String[] vlueList = asbInterface.getvVlueList(cls);
                myexuMethod(cls,asbInterface,namMap);
            }
        }
    }
    //执行方法
    public void myexuMethod(Class<?> cls,AsbInterface asbInterface,Map namMap) throws Exception {
        final Object object = cls.getConstructor(new Class[] {}).newInstance();
        final Method[] methods = cls.getDeclaredMethods();

        Set<String> setKey = namMap.keySet();
        for (String key : setKey) {
            String[] keys = key.split("\\[");
            keys = keys[1].split("\\]");
            String finalKey = keys[0];
            for (final Method method : methods) {
                if (Arrays.toString(asbInterface.getvMethodTopicList(method)).contains(finalKey)){
                    FsmetObj fsmetObj = new FsmetObj();
                    fsmetObj.method = method;
                    fsmetObj.object = object;
                    fsmetObjHashMap.put(finalKey,fsmetObj);
                }

            }
        }


    }
}
