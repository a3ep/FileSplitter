package net.bondar.core.interfaces.tasks;

import net.bondar.core.FileStatObject;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IPartObject;
import net.bondar.core.interfaces.Iterable;
import net.bondar.statistics.interfaces.IStatisticsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Provides tasks for thread pool.
 */
public abstract class AbstractTask implements ITask {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Specified file.
     */
    protected final File file;

    /**
     * Parameter holder
     */
    protected final IConfigHolder configHolder;

    /**
     * Iterator.
     */
    protected final Iterable iterator;

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
    protected IPartObject filePart;

    /**
     * Initialises <code>AbstractTask</code> fields.
     *
     * @param file              specified file
     * @param configHolder      configuration holder
     * @param iterator          iterator
     * @param statisticsService statistics service
     */
    public AbstractTask(final File file, IConfigHolder configHolder, Iterable iterator, IStatisticsService statisticsService) {
        this.file = file;
        this.configHolder = configHolder;
        this.iterator = iterator;
        this.statisticsService = statisticsService;
    }

    /**
     * Reads the byte array of the one file and writes them to another file.
     *
     * @param sourceFile file from which data is read
     * @param outputFile file in witch data is wrote
     * @param finish     end position of part-file
     * @param bufferSize default buffer size
     * @throws IOException when read/write exception is occurring
     */
    protected void readWrite(RandomAccessFile sourceFile, RandomAccessFile outputFile,
                             final long finish, final int bufferSize) throws IOException {
        while (start < finish) {
            //create buffer for copying
            byte[] array = new byte[getAvailableSize(finish, start, bufferSize)];
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
