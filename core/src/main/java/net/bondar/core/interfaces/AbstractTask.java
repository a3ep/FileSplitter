package net.bondar.core.interfaces;

import net.bondar.calculations.FileCalculationUtils;
import net.bondar.core.domain.FileStatObject;
import net.bondar.statistics.interfaces.IStatisticsService;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides tasks for thread pool.
 */
public abstract class AbstractTask implements ITask {

    /**
     * Specified file.
     */
    protected final File file;

    /**
     * Parameter holder
     */
    protected final IConfigHolder paramHolder;

    /**
     * Iterator.
     */
    protected final Iterable iterator;

    /**
     * Statistic service.
     */
    private final IStatisticsService statisticsService;

    /**
     * Interrupt flag.
     */
    protected AtomicBoolean interrupt;

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
     * @param interrupt         interrupt flag
     * @param paramHolder       parameter holder
     * @param iterator          iterator
     * @param statisticsService statistics service
     */
    public AbstractTask(File file, AtomicBoolean interrupt, IConfigHolder paramHolder, Iterable iterator, IStatisticsService statisticsService) {
        this.file = file;
        this.interrupt = interrupt;
        this.paramHolder = paramHolder;
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
    protected void readWrite(RandomAccessFile sourceFile, RandomAccessFile outputFile, long finish, int bufferSize) throws IOException {
        while (start < finish) {
            //create buffer for copying
            byte[] array = new byte[FileCalculationUtils.getAvailableSize(finish, start, bufferSize)];
            //process of copying
            sourceFile.read(array);
            outputFile.write(array);
            long writtenSize = array.length;
            start += writtenSize;
            totalWritten += writtenSize;
            filePart.setWrittenSize(start - filePart.getStartPosition());
            filePart.setTotalWrittenSize(totalWritten);
            statisticsService.holdInformation(Thread.currentThread().getName(),
                    new FileStatObject(filePart.getStartPosition(), filePart.getEndPosition(), filePart.getWrittenSize(),
                            filePart.getTotalWrittenSize(), filePart.getFileSize()));
        }
    }
}
