package net.bondar.splitter.domain;

import net.bondar.splitter.interfaces.AbstractTask;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.splitter.interfaces.Iterable;
import net.bondar.statistics.interfaces.IStatisticService;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Provides merge tasks for thread pool.
 */
public class MergeTask extends AbstractTask {

    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(getClass());

    /**
     * Creates <code>MergeTask</code> instance.
     *
     * @param file        specified file
     * @param paramHolder parameter holder
     * @param iterator    iterator
     * @param statService statistic service
     * @see {@link AbstractTask}
     */
    public MergeTask(File file, IParameterHolder paramHolder, Iterable iterator, IStatisticService statService) {
        super(file, paramHolder, iterator, statService);

    }

    /**
     * Runs merging files into complete file.
     */
    @Override
    public void run() {
        filePart = iterator.getNext();
        while (!filePart.getStatus().equals("NULL")) {
            File part = filePart.getPartFile();
            try (RandomAccessFile sourceFile = new RandomAccessFile(part, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(file, "rw")) {
                log.info("Start to write " + part.getName() + " into Complete file : " + file.getName());
                start = filePart.getStartPosition();
                long finish = filePart.getEndPosition();
                int bufferSize = Integer.parseInt(paramHolder.getValue("bufferSize"));
                //Set the file-pointer to the start position of partFile
                outputFile.seek(start);
                // write data into file
                readWrite(sourceFile, outputFile, finish, bufferSize);
                log.info("Finish to write " + part.getName() + " into " + file.getName());
                filePart = iterator.getNext();
            } catch (IOException e) {
                log.warn("Catches IOException, during writing " + part.getName() + " into " + file.getName() + ". Message " + e.getMessage());
                return;
            }
        }
    }
}
