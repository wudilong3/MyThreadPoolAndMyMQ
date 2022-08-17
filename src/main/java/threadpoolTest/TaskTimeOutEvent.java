package threadpoolTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskTimeOutEvent {
    private BaseEvent event;
    public TaskTimeOutEvent(BaseEvent event) {
        this.event = event;
    }

    public void execute() {
        ThreadPoolTest pool = ThreadPoolTest.getInstance();
        pool.addWorkThread();
        pool.removrWorkThread(event.workThread);
        Integer obj = event.workThread.getKey();
        log.info("正在停止{}号工作线程。。。。",obj);
        event.nowThread.stop();
        log.info("已经停止{}号工作线程。。。。",obj);

    }
}
