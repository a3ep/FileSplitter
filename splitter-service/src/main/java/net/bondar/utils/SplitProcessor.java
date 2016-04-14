package net.bondar.utils;

import net.bondar.domain.Task;
import net.bondar.exceptions.FileWritingException;
import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.IProcessor;
import net.bondar.interfaces.Iterable;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 */
public class SplitProcessor implements IProcessor {

    /**
     *
     */
    private final static Logger log = Logger.getLogger(SplitProcessor.class);

    /**
     *
     */
    private File file;

    /**
     *
     */
    private Iterable iterator;

    /**
     * @param fileDest
     * @param partSize
     * @param iteratorFactory
     */
    public SplitProcessor(String fileDest, int partSize, AbstractIteratorFactory iteratorFactory) {
        this.file = new File(fileDest);
        this.iterator = iteratorFactory.createIterator((int) file.length(), partSize);
    }

    /**
     *
     */
    @Override
    public void process() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        for (int i = 0; i < executor.getCorePoolSize(); i++) {
            executor.execute(this::processTask);
        }
    }

    /**
     *
     */
    public void processTask() {
        Task task = iterator.getNext();
        while (!task.getStatus().equals("NULL")) {
            log.info("Thread " + Thread.currentThread().getName() + " processed " + task.getName() + task.getCounter());
            File partFile = new File(file.getParent(), file.getName() + task.getPartFileName());
            try (RandomAccessFile sourceFile = new RandomAccessFile(file, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(partFile, "rw")) {
                log.info("Start to write Filepart : " + partFile.getName());
                // /Set the file-pointer to the start position of partFile
                sourceFile.seek(task.getStartPosition());
                //create buffer for copying
                byte[] array = new byte[(int)(task.getEndPosition() - task.getStartPosition())];
                //process of copying
                sourceFile.read(array);
                outputFile.write(array);
                log.info("Finish to write Filepart: " + partFile.getName());
                task = iterator.getNext();
            } catch (IOException e) {
                log.warn("Catches IOException, during writing " + partFile + ". Message " + e.getMessage());
                throw new FileWritingException("Error during writing part:" + partFile.getName() +
                        ". Exception:" + e.getMessage());
            }
        }
    }
}
