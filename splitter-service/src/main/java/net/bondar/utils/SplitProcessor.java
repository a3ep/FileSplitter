package net.bondar.utils;

import net.bondar.domain.Task;
import net.bondar.exceptions.ApplicationException;
import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.IConfigLoader;
import net.bondar.interfaces.IProcessor;
import net.bondar.interfaces.Iterable;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.*;

/**
 *
 */
public class SplitProcessor implements IProcessor {

    /**
     *
     */
    private final Logger log = Logger.getLogger("splitterLogger");

    /**
     *
     */
    private final File file;

    /**
     *
     */
    private final Iterable iterator;

    /**
     *
     */
    private final ThreadPoolExecutor pool;

    /**
     * @param fileDest
     * @param partSize
     * @param iteratorFactory
     */
    public SplitProcessor(String fileDest, int partSize, AbstractIteratorFactory iteratorFactory) {
        IConfigLoader configLoader = new ApplicationConfigLoader();
        this.file = new File(fileDest);
        this.iterator = iteratorFactory.createIterator((int) file.length(), partSize);
        final SynchronousQueue<Runnable> workerQueue = new SynchronousQueue<>();
        pool = new ThreadPoolExecutor(Integer.parseInt(configLoader.getValue("threadsCount")),
                Integer.parseInt(configLoader.getValue("threadsCount")),
                0L,
                TimeUnit.MILLISECONDS,
                workerQueue,
                new NamedThreadFactory(configLoader.getValue("threadName")),
                new RejectedExecutionHandler() {
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor pool) {
                        try {
                            pool.getQueue().put(r);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     *
     */
    @Override
    public void process() {
        for (int i = 0; i < pool.getCorePoolSize(); i++) {
            pool.execute(this::processTask);
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
                log.info("Start to write: " + partFile.getName());
                // /Set the file-pointer to the start position of partFile
                sourceFile.seek(task.getStartPosition());
                //create buffer for copying
                byte[] array = new byte[(int) (task.getEndPosition() - task.getStartPosition())];
                //process of copying
                sourceFile.read(array);
                outputFile.write(array);
                log.info("Finish to write: " + partFile.getName());
                task = iterator.getNext();
            } catch (IOException e) {
                log.warn("Catches IOException, during writing " + partFile + ". Message " + e.getMessage());
                throw new ApplicationException("Error during writing part:" + partFile.getName() +
                        ". Exception:" + e.getMessage());
            }
        }
    }
}
