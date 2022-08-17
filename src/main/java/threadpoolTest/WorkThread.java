package threadpoolTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkThread extends Thread {
    private Boolean lifeFlage = true;
    private String state;
    private WorkTast workTast;
    private Integer key;

    public WorkThread(Integer key){
        this.key = key;
        this.state = "0";
    }

    private void  setState(String state) {
        this.state = state;
    }
    public String getMyState() {
        return state;
    }
    public Integer getKey(){
        return key;
    }
    private void setShutdown() {
        this.lifeFlage = false;
    }
    public void active(){
        synchronized (this) {
            setState("2");
            notify();
        }
    }

    public void kill() {
        //如果线程属于空闲状态直接关闭
        if (this.getMyState().equals("1")) {
            log.info("正在关闭工作线程。。。线程编号"+ key.toString());
            this.setShutdown();
            this.active();
        } else if (this.getMyState().equals("2")) {
            //如果线程处于运行状态，执行完成在关闭
            log.info("等待{}线程执行完毕。。。。。。。。。。。。",key.toString());
            while (this.getMyState().equals("2")) {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("正在关闭工作线程。。。线程编号"+ key.toString());
            this.setShutdown();
            this.active();
        }
    }

    public void setWorkTast(WorkTast workTast) {
        this.workTast = workTast;
    }

    @Override
    public void run(){
        while (lifeFlage) {
            try {
                setState("1");
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                new BeginEvent(this,Thread.currentThread(),workTast).esecute();
                workTast.execute();
                new EndEvent(this,Thread.currentThread(),workTast).esecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
