package Annthesyh;

import Annthesyh.TestDire.Csumer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class Scanner {



    public void addClass(Set<Class<?>> classes, String filePath, String packageName) throws Exception {
        File[] files = new File(filePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
        assert files != null;
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (!packageName.isEmpty()) {
                    className = packageName + "." +className;
                }
                //TODO
                doAddClass(classes,className);
            } else if (file.isDirectory()){
                String filePathName = file.toString();
                String newPackageName = filePathName.substring(filePathName.lastIndexOf("classes\\")+8).replace('\\','.');
                log.info(newPackageName);
                addClass(classes,filePathName,newPackageName);
            }
        }
    }

    public void doAddClass(Set<Class<?>> classes, final String className) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return super.loadClass(name);
            }
        };
        classes.add(classLoader.loadClass(className));
    }

    //获得某包下的带有某注解的方法
    public <A extends Annotation> Set<Class<?>> getAnnotationClass(String packageName, Class<A> annotationClass) throws Exception {

        Set<Class<?>> controllers = new HashSet<>();
        Set<Class<?>> clsList = getClasses(packageName);
        if (clsList != null && clsList.size() > 0) {
            for (Class<?> cls : clsList) {
                if (cls.getAnnotation(Csumer.class) != null) {
                    controllers.add(cls);
                }
            }
        }
        return controllers;
    }

    //获得包下所有的类
    public Set<Class<?>> getClasses(String packageName) throws Exception {
//        String packageName = "";
        Set<Class<?>> classes = new HashSet<>();

        String packageDirName = packageName.replace('.','/');
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();

            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(),"UTF-8");

                //TODO 文件扫描
                addClass(classes, filePath, packageName);
            } else if ("jar".equals(protocol)) {

                JarFile jar;

                jar = ((JarURLConnection)url.openConnection()).getJarFile();

                Enumeration<JarEntry> entries = jar.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();

                    if (name.charAt(0) == '/') {
                        name = name.substring(1);
                    }

                    if (name.startsWith(packageDirName)) {
                        int idx = name.lastIndexOf('/');
                        if (idx != -1) {

                            packageName = name.substring(0,idx).replace('/','.');
                        }

                        if (name.endsWith(".class") && !entry.isDirectory()) {

                            String className = name.substring(packageName.length() + 1, name.length() - 6);
                            try {
                                classes.add(Class.forName(packageName+'-'+className));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return classes;
    }
}
