package net.bondar.core.tasks;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.interfaces.tasks.AbstractTask;
import net.bondar.statistics.interfaces.IStatisticsService;
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
     * @param file              specified file
     * @param configHolder      configuration holder
     * @param iterator          iterator
     * @param statisticsService statistics service
     * @see {@link AbstractTask}
     */
    public MergeTask(File file, IConfigHolder configHolder, Iterable iterator, IStatisticsService statisticsService) {
        super(file, configHolder, iterator, statisticsService);

    }

    /**
     * Runs merging files into complete file.
     */
    @Override
    public void run() {
        filePart = iterator.getNext();
        while (!filePart.getStatus().equals("NULL") && !Thread.currentThread().isInterrupted()) {
            File part = filePart.getPartFile();
            try (RandomAccessFile sourceFile = new RandomAccessFile(part, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(file, "rw")) {
                log.debug("Start to write " + part.getName() + " into Complete file : " + file.getName());
                start = filePart.getStartPosition();
                long finish = filePart.getEndPosition();
                int bufferSize = Integer.parseInt(configHolder.getValue("bufferSize"));
                //Sets the file-pointer to the start position of partFile
                outputFile.seek(start);
                // writes data into file
                readWrite(sourceFile, outputFile, finish, bufferSize);
                log.debug("Finish to write " + part.getName() + " into " + file.getName());
                filePart = iterator.getNext();
            } catch (IOException e) {
                log.error("Error during writing " + part.getName() + " into " + file.getName() + ". Message " + e.getMessage());
                return;
            }
        }
    }
}
