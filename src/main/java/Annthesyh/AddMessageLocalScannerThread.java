package Annthesyh;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class AddMessageLocalScannerThread extends Thread {
    private MessageQueueLocal messageQueueLocal = MessageQueueLocal.getMessageQueueLocal();
    @Override
    public void run() {
        while (true){
            java.util.Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();
            messageQueueLocal.putMessage(message);
        }
    }
}
