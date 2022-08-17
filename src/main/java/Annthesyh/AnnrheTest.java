package Annthesyh;

import Annthesyh.TestDire.Csumer;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class AnnrheTest {


    public static Map<String,FsmetObj> fsmetObjHashMap = new HashMap<>();
    public static void main(String... args) throws Exception {
        new MessageQueueTestLocalMain().start();
        new AddMessageLocalThread().start();
        new GetMessageQueuelocalTest().start();
        new AddMessageLocalScannerThread().start();
//        new AddMessageLocalThread().start();
//        Map<String , Object> beanContainer = new HashMap<>();
//        AsbInterface asbInterface = new AsbInterfaceImpl();
//        new  ExcueMethod<AnnTestC,Csumer>().parseMethod(AnnTestC.class,
//        Csumer.class, asbInterface,
//        "ccdv",
//        "789798dsfaf");
//        log.info("ttttteeeesssssssttttttttt");
    }
}
