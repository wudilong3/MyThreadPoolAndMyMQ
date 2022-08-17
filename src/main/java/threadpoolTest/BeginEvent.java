package threadpoolTest;

public class BeginEvent extends BaseEvent {
    public BeginEvent (WorkThread workThread, Thread nowThread, WorkTast workTast) {
        this.nowThread = nowThread;
        this.workTast = workTast;
        this.workThread = workThread;
    }
    @Override
    public void esecute() {
        //开始监控新城是否超时
        ThreadPoolTest threadPoolTest =ThreadPoolTest.getInstance();
        threadPoolTest.beginTaskRun(this);
    }
}
