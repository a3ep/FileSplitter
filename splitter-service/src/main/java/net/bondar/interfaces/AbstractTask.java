package net.bondar.interfaces;

import net.bondar.Calculations;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 */
public abstract class AbstractTask implements Runnable {

    /**
     *
     */
    protected final Logger log = Logger.getLogger("splitterLogger");

    /**
     *
     */
    protected long totalWritten = 0;

    /**
     *
     */
    protected long start = 0;

    /**
     *
     */
    protected IPart filePart;

    /**
     *
     */
    protected final File file;

    /**
     *
     */
    protected final IParameterHolder parameterHolder;

    /**
     *
     */
    protected final Iterable iterator;

    /**
     *
     */
    protected final IStatisticService statisticService;

    /**
     * @param file
     * @param parameterHolder
     * @param iterator
     * @param statisticService
     */
    public AbstractTask(File file, IParameterHolder parameterHolder, Iterable iterator, IStatisticService statisticService) {
        this.file = file;
        this.parameterHolder = parameterHolder;
        this.iterator = iterator;
        this.statisticService = statisticService;
    }

    /**
     * @param sourceFile
     * @param outputFile
     * @param finish
     * @param bufferSize
     * @return
     * @throws IOException
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
            statisticService.holdInformation(Thread.currentThread().getName(), filePart);
        }
    }
}
