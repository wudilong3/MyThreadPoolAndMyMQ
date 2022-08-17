package threadpoolTest;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class TaskControllerThread extends Thread {
    private ThreadPoolTest poolTest;
    private static ServerSocket serverSocket;
    private String serverFlg;
    public TaskManager taskManager;
    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public TaskControllerThread(ThreadPoolTest poolTest) {
        log.info("正在创建线程池控制线程。。。。。");
        this.poolTest = poolTest;
    }

    @Override
    public void run(){
        try {
            serverSocket = new ServerSocket(9966);
            log.info("启动守候进程，端口号{}",9966);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Boolean done = false;
        while (!done) {
            try {
                Socket incoming = serverSocket.accept();
                log.info("守候线程 incoming :"+ incoming);
                DataInputStream in = new DataInputStream(incoming.getInputStream());
                DataOutputStream out = new DataOutputStream(incoming.getOutputStream());
                String line = in.readUTF();
                if (line == null) {
                    done = true;
                } else if (line.trim().equals("11")){
                    log.info("收到关闭线程池的命令。。。。");
                    poolTest.close();
                    out.writeUTF("线程池关闭成功");
                }
                else {
                    log.info("收到管理信息："+line.trim());
                }
                incoming.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
