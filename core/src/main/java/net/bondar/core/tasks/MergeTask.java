package net.bondar.core.tasks;

import net.bondar.core.PartObjectStatus;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.interfaces.tasks.AbstractTask;
import net.bondar.core.utils.ConfigHolder;
import net.bondar.statistics.interfaces.IStatisticsService;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

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
     * @param files             specified list of pert-files
     * @param configHolder      configuration holder
     * @param iterator          iterator
     * @param statisticsService statistics service
     * @see {@link AbstractTask}
     */
    public MergeTask(final List<File> files, ConfigHolder configHolder, Iterable iterator, IStatisticsService statisticsService) {
        super(files, configHolder, iterator, statisticsService);

    }

    /**
     * Runs merging files into complete file.
     */
    @Override
    public void run() {
        filePart = iterator.getNext();
        file = new File(files.get(0).getAbsolutePath().substring(0, files.get(0).getAbsolutePath().indexOf(configHolder.getValue("partSuffix"))));
        while (!filePart.getStatus().equals(PartObjectStatus.NULL) && !Thread.currentThread().isInterrupted()) {
            log.info("Start processing task #" + filePart.getCounter() + ": " + filePart.getPartFile().getName()
                    + " by thread " + Thread.currentThread().getName());
            File part = files.get(filePart.getCounter()-1);
            try (RandomAccessFile sourceFile = new RandomAccessFile(part, "r");
                 RandomAccessFile outputFile = new RandomAccessFile(file, "rw")) {
                log.debug("Start to write " + part.getName() + " into Complete file : " + file.getName());
                start = filePart.getStartPosition();
                long finish = filePart.getEndPosition();
                int bufferSize = Integer.parseInt(configHolder.getValue("bufferSize"));
                //Sets the file-pointer to the start position of partFile
                outputFile.seek(start);
                // writes data into file
                log.debug("Start read-write operation, from " + part.getName() + " to " + file.getName());
                readWrite(sourceFile, outputFile, finish, bufferSize);
                log.debug("Finish to write " + part.getName() + " into " + file.getName());
                log.info("Finish processing task #" + filePart.getCounter() + ": " + filePart.getPartFileName());
                filePart = iterator.getNext();
            } catch (IOException e) {
                log.error("Error during writing " + part.getName() + " into " + file.getName() + ". Message " + e.getMessage());
                return;
            }
        }
    }
}
