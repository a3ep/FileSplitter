package net.bondar.utils;

import net.bondar.domain.Task;
import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.IProcessor;
import net.bondar.interfaces.Iterable;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 */
public class SplitProcessor implements IProcessor {

    /**
     *
     */
    private String fileDest;

    /**
     *
     */
    private int partSize;
    /**
     *
     */

    private File file;
    /**
     *
     */
    private AbstractIteratorFactory iteratorFactory;

    private Iterable iterator;

    /**
     *
     * @param fileDest
     * @param partSize
     * @param iteratorFactory
     */
    public SplitProcessor(String fileDest, int partSize, AbstractIteratorFactory iteratorFactory) {
        this.fileDest = fileDest;
        this.partSize = partSize;
        this.iteratorFactory = iteratorFactory;
        this.file=new File(fileDest);
        this.iterator = iteratorFactory.createIterator((int)file.length(), partSize);
    }

   public void process(){
       ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
       for(int i=0; i<executor.getCorePoolSize();i++){
           executor.execute(this::processTask);
       }
   }

    /**
     *
     */
    public void processTask() {
        System.out.println("Thread "+Thread.currentThread().getName() + " processed task");
        while (true) {
            Task task = iterator.getNext();
            byte[] buffer = new byte[task.getStartPosition() + task.getEndPosition()];
            if (task.getPartFileName().equals("")) {
                return;
            }
            try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                FileOutputStream fos = new FileOutputStream(new File(file.getParent(), file.getName()+task.getPartFileName()))){
                bis.read(buffer, task.getStartPosition(), task.getEndPosition());
                fos.write(buffer);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
