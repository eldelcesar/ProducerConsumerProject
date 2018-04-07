package producerconsumer;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.sun.org.apache.xerces.internal.parsers.CachingParserPool.SynchronizedGrammarPool;

import javax.swing.*;

public class QueueBuffer {
    Queue<String> Buffer;
    int maxSize, producedCount, processedCount;
    DefaultListModel bufferQueue;

    public QueueBuffer(int capacity, DefaultListModel list) {
        Buffer = new LinkedList<>();
        maxSize = capacity;
        bufferQueue = list;
    }

    public int getProcessedCount(){ return  processedCount; }

    public int getProducedCount(){ return producedCount; }

    // TODO NOTIFY UI FOR CHANGES, BUT DO NOT MODIFY IT IN THIS THREAD.
    synchronized String consume(){
        String product = "";
        if(product.equals("")){
            try {
                wait(100);
            } catch (Exception e) {
                Logger.getLogger(QueueBuffer.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        product = this.Buffer.poll();
        if(product == null){
            notify();
            return "";
        }

        notify();
        processedCount++;
        return product;
    }


    // TODO NOTIFY UI FOR CHANGES, BUT DO NOT MODIFY IT IN THIS THREAD.
    synchronized boolean produce(String operation){
        if(Buffer.size() < maxSize){
            this.Buffer.add(operation);
            producedCount++;
            bufferQueue.addElement(operation);
            notify();
            return true;
        }
        return false;
    }
}