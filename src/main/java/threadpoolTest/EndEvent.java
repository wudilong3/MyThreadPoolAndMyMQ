package threadpoolTest;

public class EndEvent extends BaseEvent {
    public EndEvent (WorkThread workThread, Thread nowThread, WorkTast workTast) {
        this.nowThread = nowThread;
        this.workTast = workTast;
        this.workThread = workThread;
    }
    @Override
    public void esecute() {
        //正常结束线程监控
        ThreadPoolTest threadPoolTest = ThreadPoolTest.getInstance();
        threadPoolTest.endTaskRun(this);
    }
}
