package threadpoolTest;

public class TaskRunTime {
    private long beginTime;
    private long endTime;
    private BeginEvent beginEvent;

    public TaskRunTime(BeginEvent beginEvent){
        this.beginEvent = beginEvent;
        this.beginTime = System.currentTimeMillis();
        this.endTime = this.beginTime;
    }

    public BeginEvent getBeginEvent() {
        return beginEvent;
    }

    //检查是否超时
    public boolean checkRunTimeOut(long mixTime) {
        endTime = System.currentTimeMillis();
        long cha = endTime - beginTime;
        return cha >= mixTime;
    }
}
