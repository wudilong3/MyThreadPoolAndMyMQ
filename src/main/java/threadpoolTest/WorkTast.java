package threadpoolTest;

public interface WorkTast {
    void execute() throws Exception;
    void setTaskThreadKey(Integer key);
    Integer getTaskThreadKey();
    void setTaskBean(TaskBean taskBean);
    TaskBean getTaskBean();
}
