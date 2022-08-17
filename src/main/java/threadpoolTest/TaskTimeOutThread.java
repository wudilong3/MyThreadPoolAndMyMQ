package threadpoolTest;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TaskTimeOutThread extends Thread{
    private ThreadPoolTest threadPoolTest;
    private List<TaskRunTime> taskRunTimeList = new ArrayList<TaskRunTime>();
    private Boolean lifeFlage = true;
    public TaskTimeOutThread(ThreadPoolTest threadPoolTest) {
        this.threadPoolTest = threadPoolTest;
    }
    @Override
    public void run() {
        while (lifeFlage) {
            for (TaskRunTime t : taskRunTimeList){
                if (t.checkRunTimeOut(5000)){
                    taskRunTimeList.remove(t);
                    new TaskTimeOutEvent(t.getBeginEvent()).execute();
                    break;
                }
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //任务运行开始检测
    public void beginTaskRun(BeginEvent beginEvent) {
        taskRunTimeList.add(new TaskRunTime(beginEvent));
    }

    //任务正常结束
    public void endTaskRun(EndEvent endEvent) {
        log.info("{}号线程，{}任务正常结束",endEvent.workTast.getTaskThreadKey(),
                endEvent.workTast.getTaskBean().getTaskName() );
        for (TaskRunTime t : taskRunTimeList) {
            BeginEvent beginEvent = t.getBeginEvent();
            if (beginEvent.equals(endEvent)){
                taskRunTimeList.remove(t);
                break;
            }
        }
    }

    //自杀
    public void kill() {
        log.info("正在关闭超时检测线程。。。。。");
        while (taskRunTimeList.size() > 0) {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lifeFlage = false;
    }
}
