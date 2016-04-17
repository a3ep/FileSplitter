package net.bondar.splitter.interfaces;

import net.bondar.Calculations;
import net.bondar.statistics.interfaces.IPartObject;
import net.bondar.statistics.interfaces.IStatisticService;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Provides tasks for thread pool.
 */
public abstract class AbstractTask implements Runnable {

    /**
     * Logger.
     */
    protected final Logger log = Logger.getLogger("splitterLogger");

    /**
     * Total written size.
     */
    protected long totalWritten = 0;

    /**
     * Start position index.
     */
    protected long start = 0;

    /**
     * Object contains part-file parameters.
     */
    protected IPartObject filePart;

    /**
     * Specified file.
     */
    protected final File file;

    /**
     * Parameter holder
     */
    protected final IParameterHolder paramHolder;

    /**
     * Iterator.
     */
    protected final Iterable iterator;

    /**
     * Statistic service.
     */
    protected final IStatisticService statService;

    /**
     * Initialises <code>AbstractTask</code> fields.
     *
     * @param file        specified file
     * @param paramHolder parameter holder
     * @param iterator    iterator
     * @param statService statistic service
     */
    public AbstractTask(File file, IParameterHolder paramHolder, Iterable iterator, IStatisticService statService) {
        this.file = file;
        this.paramHolder = paramHolder;
        this.iterator = iterator;
        this.statService = statService;
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
            byte[] array = new byte[Calculations.getAvailableSize(finish, start, bufferSize)];
            //process of copying
            sourceFile.read(array);
            outputFile.write(array);
            long writtenSize = array.length;
            start += writtenSize;
            totalWritten += writtenSize;
            filePart.setWrittenSize(start - filePart.getStartPosition());
            filePart.setTotalWrittenSize(totalWritten);
            statService.holdInformation(Thread.currentThread().getName(), filePart);
        }
    }
}
