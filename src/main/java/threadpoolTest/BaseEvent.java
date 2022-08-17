package threadpoolTest;

public class BaseEvent {
    protected WorkTast workTast;
    protected WorkThread workThread;
    protected Thread nowThread;

    //事件执行方法
    public void esecute() {}

    // equals 方法 重写
    @Override
    public  boolean equals(Object obj){
        BaseEvent other = (BaseEvent)obj;
        return this.workThread == other.workThread
                && this.workTast == other.workTast;
    }
}
