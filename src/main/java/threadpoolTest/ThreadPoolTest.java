package threadpoolTest;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ThreadPoolTest {
    private static ThreadPoolTest threadPoolTest = null;
    private static List<WorkThread> workThreadList = new ArrayList<WorkThread>();
    private static Integer workThreadNum = 10;
    private static Integer allThreadNun;
    private static Boolean noGetLazyThred = false;
    private static TaskManager taskManager;
    private static TaskMainThread taskMainThread;
    private static TaskControllerThread taskControllerThread;
    private static TaskTimeOutThread taskTimeOutThread;
    private ThreadPoolTest(){

    }

    public static TaskManager getTaskManager() {
        return taskManager;
    }

    public static synchronized ThreadPoolTest getInstance() {
        if (threadPoolTest == null) {
            log.info("pool不存在，重新初始化");
            init(10);
        }
        return threadPoolTest;
    }

    //初始化线程池
    private static void init(int count) {
        log.info("线程池初始化开始。。。。。。。。。。。");
        if (threadPoolTest == null) {
            taskManager = new TaskManager();
            threadPoolTest = new ThreadPoolTest();
            allThreadNun = count + 3;
            for (int i=0;i<count;i++) {
                WorkThread workThread = new WorkThread(i);
                workThreadList.add(workThread);
                workThread.start();
            }
            log.info("threadPoolTest线程池创建" + threadPoolTest);
            taskMainThread = new TaskMainThread(taskManager,threadPoolTest);
//            taskMainThread.setTaskManager(taskManager);
            taskMainThread.start();
            taskTimeOutThread = new TaskTimeOutThread(threadPoolTest);
            taskTimeOutThread.start();
            taskControllerThread = new TaskControllerThread(threadPoolTest);
            taskControllerThread.setTaskManager(taskManager);
            taskControllerThread.start();
        }
        log.info("线程池初始化完成。。。。。。。。。。。。。。。。。");
    }

    public WorkThread getLazyThread() {
        while (true) {
            if (noGetLazyThred) {
                return null;
            }
            for (WorkThread workThread : workThreadList) {
                if ("1".equals(workThread.getMyState())) {
                    return workThread;
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //添加线程
    public void addWorkThread(){
        WorkThread workThread = new WorkThread(++workThreadNum);
        workThreadList.add(workThread);
        workThread.start();
        ++allThreadNun;
    }

    //移除线程
    public void removrWorkThread(WorkThread workThread){
        workThreadList.remove(workThread);
        --allThreadNun;
    }

    private static void stopGetIdleThread() {
        noGetLazyThred = false;
    }

    //任务运行，注册监视
    public void beginTaskRun(BeginEvent beginEvent) {
        taskTimeOutThread.beginTaskRun(beginEvent);
    }
    //任务结束，注销监视
    public void endTaskRun(EndEvent endEvent) {
        taskTimeOutThread.endTaskRun(endEvent);
    }

    //关闭线程池
    public static void close() {
        //停止获取空闲线程
        stopGetIdleThread();
        //杀死主控线程
        taskMainThread.kill();
        //杀死超时监控线程
        taskTimeOutThread.kill();
        //杀死工作线程
        for (WorkThread workThread : workThreadList) {
            workThread.kill();
        }
    }
}
