package net.bondar.core.tasks;

import net.bondar.core.FilePartObject;
import net.bondar.core.FileStatObject;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.interfaces.tasks.ITask;
import net.bondar.core.utils.ConfigHolder;
import net.bondar.statistics.interfaces.IStatisticsService;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides tasks for thread pool.
 */
public abstract class AbstractTask implements ITask {

    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(getClass());

    /**
     * Specified file.
     */
    protected File file;

    /**
     * Specified list of part-files.
     */
    protected List<File> files;

    /**
     * Parameter holder
     */
    protected final ConfigHolder configHolder;

    /**
     * Iterator.
     */
    final Iterable iterator;

    /**
     * Statistic service.
     */
    private final IStatisticsService statisticsService;

    /**
     * Total written size.
     */
    private long totalWritten = 0;

    /**
     * Start position index.
     */
    protected long start = 0;

    /**
     * Object contains part-file parameters.
     */
    FilePartObject filePart;

    /**
     * Initialises <code>AbstractTask</code> fields.
     *
     * @param file              specified file
     * @param configHolder      configuration holder
     * @param iterator          iterator
     * @param statisticsService statistics service
     */
    AbstractTask(final File file, ConfigHolder configHolder, Iterable iterator, IStatisticsService statisticsService) {
        this.file = file;
        this.files = new ArrayList<>();
        this.configHolder = configHolder;
        this.iterator = iterator;
        this.statisticsService = statisticsService;
    }

    /**
     * Initialises <code>AbstractTask</code> fields.
     *
     * @param files             specified part-files
     * @param configHolder      configuration holder
     * @param iterator          iterator
     * @param statisticsService statistics service
     */
    AbstractTask(final List<File> files, ConfigHolder configHolder, Iterable iterator, IStatisticsService statisticsService) {
        this.file = null;
        this.files = files;
        this.configHolder = configHolder;
        this.iterator = iterator;
        this.statisticsService = statisticsService;
    }

    /**
     * Reads the byte array of the one file and writes them to another file.
     *
     * @param sourceFile file from which data is read
     * @param outputFile file in witch data is wrote
     * @param filePart   object with file parameters
     * @throws IOException when read/write exception is occurring
     */
    void readWrite(RandomAccessFile sourceFile, RandomAccessFile outputFile,
                   final FilePartObject filePart) throws IOException {
        log.debug("Start read-write operation from " + filePart.getPartFileName() + " to " + file.getName());
        start = filePart.getStartPosition();
        long finish = filePart.getEndPosition();
        while (start < finish) {
            //create buffer for copying
            byte[] array = new byte[getAvailableSize(finish, start, Integer.parseInt(configHolder.getValue("bufferSize")))];
            //process of copying
            sourceFile.read(array);
            outputFile.write(array);
            long writtenSize = array.length;
            start += writtenSize;
            totalWritten += writtenSize;
            statisticsService.holdInformation(Thread.currentThread().getName(),
                    new FileStatObject(filePart.getStartPosition(), filePart.getEndPosition(),
                            start - filePart.getStartPosition(), totalWritten, filePart.getFileSize()));
        }
        log.debug("Finish to write " + filePart.getPartFileName() + " into " + file.getName());
        log.info("Finish processing task #" + filePart.getCounter() + ": " + filePart.getPartFileName());
    }

    /**
     * Gets the required size of the byte array to write into the file.
     *
     * @param finish     index of the end position in a file.
     * @param start      index of the start position in a file.
     * @param bufferSize default buffer size
     * @return required byte array size
     */
    private int getAvailableSize(final long finish, final long start, final int bufferSize) {
        return ((finish - start) >= bufferSize) ? bufferSize : (int) (finish - start);
    }
}
