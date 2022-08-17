package threadpoolTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkTastImpl implements WorkTast {
    private Integer key;
    private TaskBean taskBean;
    public void execute() throws Exception {
        log.info("正在执行：任务实现类"+key+taskBean.getTaskName());
        Thread.sleep(2000);
    }

    public void setTaskThreadKey(Integer key) {
        this.key = key;
    }

    public Integer getTaskThreadKey() {
        return this.key;
    }

    public void setTaskBean(TaskBean taskBean) {
        this.taskBean = taskBean;
    }

    public TaskBean getTaskBean() {
        return taskBean;
    }
}
