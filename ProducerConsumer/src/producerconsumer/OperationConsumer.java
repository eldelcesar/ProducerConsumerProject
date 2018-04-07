package producerconsumer;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperationConsumer extends Thread {

    private QueueBuffer Buffer;
    private OperationParser parser = new OperationParser();
    private long TimeToWait;
    private DefaultListModel<String> doneList;
    private int Id;
    private boolean isConsuming;
    private JTextArea doneOps;

    public OperationConsumer(QueueBuffer buffer,
                             int timeToWait,
                             DefaultListModel<String> list,
                             int id) {
        Buffer = buffer;
        TimeToWait = timeToWait;
        doneList = list;
        Id = id;
        isConsuming = true;
    }

    @Override
    public void run() {
        String product = "";

        // TODO NOTIFY UI FOR CHANGES, BUT DO NOT MODIFY IT IN THIS THREAD.
        while (isConsuming) {
            product = this.Buffer.consume();
            if(product.equals("")){
                continue;
            }
            String result = product + " = " + parser.evaluate(product);
            doneList.addElement(result);
            try {
                Thread.sleep(TimeToWait);
            } catch (Exception e) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void stopConsuming(){
        isConsuming = false;
        System.out.println("[C" + Id + "] " + " stopped consuming");
    }
}