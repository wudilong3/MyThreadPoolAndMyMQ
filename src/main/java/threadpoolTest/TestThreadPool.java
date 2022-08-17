package threadpoolTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestThreadPool {

    public static void testRun() {
        log.info("测试线程池初始化");
        for (int i = 0;i<10;i++) {
            int finalI = i;
            new Thread(() -> {
                for (int j = 0;j<100;j++) {
                    ThreadPoolTest threadPoolTest = ThreadPoolTest.getInstance();
                    TaskManager taskManager = threadPoolTest.getTaskManager();
                    WorkTast workTast = new WorkTastImpl();
                    TaskBean taskBean = new TaskBean();
                    taskBean.setTaskName("测试任务：线程"+ finalI +"：任务数"+j);
                    taskBean.setTaskPath("测试地址：线程"+ finalI +"：任务数"+j);
                    workTast.setTaskBean(taskBean);
                    taskManager.addWorkTask(workTast);
                }
            }).start();
        }

    }

    public static void main(String... args) {
        testRun();
    }
}
