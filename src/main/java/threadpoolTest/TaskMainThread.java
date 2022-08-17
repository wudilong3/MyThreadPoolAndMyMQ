package threadpoolTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskMainThread extends Thread {
    private ThreadPoolTest threadPoolTest;
    private TaskManager taskManager;
    private Boolean lifeFlage = true;

    public TaskMainThread(TaskManager taskManager,ThreadPoolTest threadPoolTest) {
        this.taskManager = taskManager;
        this.threadPoolTest = threadPoolTest;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
    private void setLifeFlage() {
        this.lifeFlage = false;
    }

    @Override
    public void run() {
        while (lifeFlage) {
            WorkTast workTast = taskManager.getworkTast();
            if (workTast == null) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                WorkThread workThread = threadPoolTest.getLazyThread();
                if (workTast == null) break;
                workThread.setWorkTast(workTast);
                workTast.setTaskThreadKey(workThread.getKey());
                workThread.active();
                try {
                    log.info("等待50毫秒，继续扫描任务");
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void kill() {
        log.info("正在关闭任务检测线程。。。");
        setLifeFlage();
    }
}
