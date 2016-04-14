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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class MergeProcessor implements IProcessor {

    /**
     *
     */
    private final Logger log = Logger.getLogger("splitterLogger");

    /**
     *
     */
    private final String completeFileName;
    /**
     *
     */
    private final Iterable iterator;

    private final ThreadPoolExecutor pool;

    /**
     *
     * @param partFileDest
     * @param iteratorFactory
     */
    public MergeProcessor(String partFileDest, AbstractIteratorFactory iteratorFactory) {
        IConfigLoader configLoader = new ApplicationConfigLoader();
        this.completeFileName = partFileDest.substring(0, partFileDest.indexOf("_"));
        List<File> parts = getPartsList(partFileDest);
        this.iterator = iteratorFactory.createIterator(parts);
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
            File part = task.getFile();
            try (RandomAccessFile sourceFile = new RandomAccessFile(part, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(completeFileName, "rw")) {
                log.info("Start to write " + part.getName() + " into Complete file : " + completeFileName);
                byte[] array = new byte[(int) part.length()];

                //Set the file-pointer to the start position of partFile
                long start = task.getStartPosition();
                outputFile.seek(start);

                //process of copying
                sourceFile.read(array);
                outputFile.write(array);
                log.info("Finish to write " + part.getName() + " into " + completeFileName);
                task = iterator.getNext();
            } catch (IOException e) {
                log.warn("Catches IOException, during writing " + part.getName() + " into " + completeFileName + ". Message " + e.getMessage());
                throw new ApplicationException("Error during writing part:" + part.getName() +
                        " into " + completeFileName + ". Exception:" + e.getMessage());
            }
        }
    }

    /**
     * @param dest
     * @return
     */
    private List<File> getPartsList(String dest) {
        File partFile = new File(dest);
        String partName = partFile.getName();
        String destName = partName.substring(0, partName.indexOf("_part_"));
        File file = partFile.getParentFile();
        File[] files = file.listFiles((File dir, String name) -> name.matches(destName + ".+\\_\\d+"));
        Arrays.sort(files);
        List<File> parts = new LinkedList<>();
        Collections.addAll(parts, files);
        return parts;
    }
}
