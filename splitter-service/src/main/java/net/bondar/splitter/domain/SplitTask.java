package net.bondar.splitter.domain;

import net.bondar.splitter.interfaces.AbstractTask;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.splitter.interfaces.IProcessor;
import net.bondar.splitter.interfaces.Iterable;
import net.bondar.statistics.interfaces.IStatisticService;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Provides split tasks for thread pool.
 */
public class SplitTask extends AbstractTask {

    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(getClass());

    /**
     * Creates <code>SplitTask</code> instance.
     *
     * @param file        specified file
     * @param paramHolder parameter holder
     * @param iterator    iterator
     * @param statService statistic service
     * @see {@link AbstractTask}
     */
    public SplitTask(File file, IProcessor processor, IParameterHolder paramHolder, Iterable iterator, IStatisticService statService) {
        super(file, processor, paramHolder, iterator, statService);
    }

    /**
     * Runs splitting the file onto parts.
     */
    @Override
    public void run() {
        filePart = iterator.getNext();
        while (!filePart.getStatus().equals("NULL") && !processor.getInterrupt()) {
            File partFile = new File(file.getParent(), file.getName() + filePart.getPartFileName());
            try (RandomAccessFile sourceFile = new RandomAccessFile(file, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(partFile, "rw")) {
                log.debug("Start to write: " + partFile.getName());
                // /Set the file-pointer to the start position of partFile
                sourceFile.seek(filePart.getStartPosition());
                start = filePart.getStartPosition();
                long finish = filePart.getEndPosition();
                int bufferSize = Integer.parseInt(paramHolder.getValue("bufferSize"));
                // write data into file
                readWrite(sourceFile, outputFile, finish, bufferSize);
                log.debug("Finish to write: " + partFile.getName());
                filePart = iterator.getNext();
            } catch (IOException e) {
                log.warn("Catches IOException, during writing " + partFile + ". Message " + e.getMessage());
                return;
            }
        }
    }
}
