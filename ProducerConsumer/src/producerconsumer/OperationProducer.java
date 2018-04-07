package producerconsumer;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperationProducer extends Thread {

    private QueueBuffer Buffer;
    private OperationBuilder Builder;
    private long TimeToWait;
    private DefaultListModel<String> todoList;
    private boolean isProducing;
    private int Id;
    private JTextArea toDoOps;

    public OperationProducer(QueueBuffer buffer,
                             int timeToWait,
                             OperationBuilder builder,
                             DefaultListModel<String> list,
                             int id) {
        Buffer = buffer;
        TimeToWait = timeToWait;
        Builder = builder;
        todoList = list;
        isProducing = true;
        Id = id;
    }


    @Override
    public void run() {
        String product;

        // TODO NOTIFY UI FOR CHANGES, BUT DO NOT MODIFY IT IN THIS THREAD.
        while (isProducing) {
            product = Builder.generate();
            boolean isAdd = this.Buffer.produce(product);
            todoList.addElement("[P" + Id + "] " + product);
            
            try {
                Thread.sleep(TimeToWait);
            } catch (Exception e) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, e);
            }

            if (isAdd){
                todoList.removeElement("[P" + Id + "] " + product);
            }
        }
    }

    public void stopProducing(){
        isProducing = false;
        System.out.println("[P" + Id + "] " + " stopped producing");
        // todoListModel.clear();
    }
}