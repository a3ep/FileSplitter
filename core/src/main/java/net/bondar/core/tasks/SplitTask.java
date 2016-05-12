package net.bondar.core.tasks;

import net.bondar.core.PartObjectStatus;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.utils.ConfigHolder;
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
    public SplitTask(final File file, ConfigHolder configHolder, Iterable iterator, IStatisticsService statisticsService) {
        super(file, configHolder, iterator, statisticsService);
    }

    /**
     * Runs splitting the file onto parts.
     */
    @Override
    public void run() {
        filePart = iterator.getNext();
        while (!filePart.getStatus().equals(PartObjectStatus.NULL) && !Thread.currentThread().isInterrupted()) {
            log.info("Start processing task #" + filePart.getCounter() + ": " + filePart.getPartFileName()
                    + " by thread " + Thread.currentThread().getName());
            File partFile = new File(file.getParent(), file.getName() + filePart.getPartFileName());
            try (RandomAccessFile sourceFile = new RandomAccessFile(file, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(partFile, "rw")) {
                log.debug("Start to write: " + partFile.getName());
                // /Sets the file-pointer to the start position of partFile
                sourceFile.seek(filePart.getStartPosition());
                // writes data into file
                readWrite(sourceFile, outputFile, filePart);
                filePart = iterator.getNext();
            } catch (IOException e) {
                log.error("Error during writing " + partFile + ". Message " + e.getMessage());
                return;
            }
        }
    }
}
