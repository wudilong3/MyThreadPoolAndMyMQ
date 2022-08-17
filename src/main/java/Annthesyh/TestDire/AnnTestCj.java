package Annthesyh.TestDire;

import Annthesyh.AnnTestC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Csumer
public class AnnTestCj implements AnnTestC {
    @Csumer
    private String name;

    @Csumer
    private String age;

    @Csumer
    public String getName () {
        return name;
    }

    @Csumer
    public String getAge () {
        return age;
    }
    @Csumer
    public void annTest() {
        log.info("测试方法");
    }

    @Override
    @Csumer(Topic = "topic" , Partition = "99000")
    public void getTest(String name) {
        log.info("测试方法" + name);
    }


    @Csumer(Topic = "ccdv" , Partition = "100")
    public void getTest1(String name) {
//        if("99999".equals(name))
        log.info("测试方法yyyyyy" + name);
    }
    @Csumer(Topic = "tty" , Partition = "0")
    public void getTest1tty(String name) {
        log.info("测试方法yy23yyyy" + name);
    }
    @Csumer(Topic = "sdf" , Partition = "0")
    public void getTest1sdf(String name) {
        log.info("测试方法yy44yyyy" + name);
    }
}
