package net.bondar.utils;

import net.bondar.domain.Task;
import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.IProcessor;
import net.bondar.interfaces.Iterable;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 */
public class SplitProcessor implements IProcessor {
    private final static Logger log = Logger.getLogger(SplitProcessor.class);
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
       ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
       for(int i=0; i<executor.getCorePoolSize();i++){
           executor.execute(this::processTask);
       }
   }

    /**
     *
     */
    public void processTask() {
        System.out.println("Thread "+Thread.currentThread().getName() + " processed task");
        Task task = iterator.getNext();
        while (!task.getStatus().equals("NULL")) {
            int byteLength = task.getEndPosition() - task.getStartPosition();
            log.info("byteLength="+byteLength);
            byte[] buffer = new byte[byteLength];
            try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                FileOutputStream fos = new FileOutputStream(new File(file.getParent(), file.getName()+task.getPartFileName()))){
                bis.read(buffer, task.getStartPosition(), byteLength);
                fos.write(buffer);
            }catch(IOException e){
                e.printStackTrace();
            }
            task=iterator.getNext();
        }
    }
}
