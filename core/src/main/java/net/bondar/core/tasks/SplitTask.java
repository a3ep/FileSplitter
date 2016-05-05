package net.bondar.core.tasks;

import net.bondar.core.PartObjectStatus;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.interfaces.tasks.AbstractTask;
import net.bondar.statistics.interfaces.IStatisticsService;
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
     * @param file              specified file
     * @param configHolder      configuration holder
     * @param iterator          iterator
     * @param statisticsService statistics service
     * @see {@link AbstractTask}
     */
    public SplitTask(final File file, IConfigHolder configHolder, Iterable iterator, IStatisticsService statisticsService) {
        super(file, configHolder, iterator, statisticsService);
    }

    /**
     * Runs splitting the file onto parts.
     */
    @Override
    public void run() {
        filePart = iterator.getNext();
        while (!filePart.getStatus().equals(PartObjectStatus.NULL) && !Thread.currentThread().isInterrupted()) {
            File partFile = new File(file.getParent(), file.getName() + filePart.getPartFileName());
            try (RandomAccessFile sourceFile = new RandomAccessFile(file, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(partFile, "rw")) {
                log.debug("Start to write: " + partFile.getName());
                // /Sets the file-pointer to the start position of partFile
                sourceFile.seek(filePart.getStartPosition());
                start = filePart.getStartPosition();
                long finish = filePart.getEndPosition();
                int bufferSize = Integer.parseInt(configHolder.getValue("bufferSize"));
                // writes data into file
                readWrite(sourceFile, outputFile, finish, bufferSize);
                log.debug("Finish to write: " + partFile.getName());
                filePart = iterator.getNext();
            } catch (IOException e) {
                log.error("Error during writing " + partFile + ". Message " + e.getMessage());
                return;
            }
        }
    }
}
