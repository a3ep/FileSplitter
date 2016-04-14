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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 */
public class MergeProcessor implements IProcessor {
    private final static Logger log = Logger.getLogger(SplitProcessor.class);

    /**
     *
     */
    private File completeFile;

    /**
     *
     */
    private Iterable iterator;

    /**
     * @param partFileDest
     * @param iteratorFactory
     */
    public MergeProcessor(String partFileDest, AbstractIteratorFactory iteratorFactory) {
        this.completeFile = new File(partFileDest.substring(0, partFileDest.indexOf("_")));
        List<File> parts = getPartsList(partFileDest);
        this.iterator = iteratorFactory.createIterator(parts);
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

    public void processTask() {
        Task task = iterator.getNext();
        while (!task.getStatus().equals("NULL")) {
            log.info("Thread " + Thread.currentThread().getName() + " processed " + task.getName() + task.getCounter());
            File part = task.getFile();
            try (RandomAccessFile sourceFile = new RandomAccessFile(part, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(completeFile, "rw")) {
                log.info("Start to write " + part.getName() + " into Complete file : " + completeFile.getName());
                byte[] array = new byte[(int) part.length()];

                //Set the file-pointer to the start position of partFile
                long start = task.getStartPosition();
                outputFile.seek(start);

                //process of copying
                sourceFile.read(array);
                outputFile.write(array);
                log.info("Finish to write " + part.getName() + " into " + completeFile.getName());
                task = iterator.getNext();
            } catch (IOException e) {
                log.warn("Catches IOException, during writing " + part + " into " + completeFile + ". Message " + e.getMessage());
                throw new FileWritingException("Error during writing part:" + part.getName() +
                        " into " + completeFile.getName() + ". Exception:" + e.getMessage());
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
