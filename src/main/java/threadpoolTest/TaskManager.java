package threadpoolTest;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TaskManager {
    private List<WorkTast> workTastList = new ArrayList<WorkTast>();

    public TaskManager(){}

    //添加任务
    private synchronized void addTask(WorkTast workTast) {
        this.workTastList.add(workTast);
    }
    //获取任务
    public synchronized WorkTast getworkTast() {
        if (workTastList.size()>0){
            return workTastList.remove(0);
        }else {
            return null;
        }
    }
    //单条添加任务
    public void addWorkTask(WorkTast workTast) {
        this.addTask(workTast);
    }
    //批量添加任务
    private void addWotkTaskList(List<WorkTast> workTastList) {
        log.info("新增的任务条数：{}",workTastList.size());
        List<WorkTast> oldWorkTastList = this.workTastList;
        for (WorkTast workTast : workTastList){
            if (!oldWorkTastList.contains(workTast)) {
                this.addTask(workTast);
            }
        }
    }

    //获取任务队列
    public List<WorkTast> getWorkTastList() {
        return this.workTastList;
    }
}
