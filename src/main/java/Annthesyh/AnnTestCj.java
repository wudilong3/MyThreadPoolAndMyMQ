package Annthesyh;

import Annthesyh.TestDire.Csumer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Csumer(Topic = "32423432")
@Data
public class AnnTestCj {
    @Csumer(Topic = {"fafasdfa","sfsfasdafsda"})
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
}
