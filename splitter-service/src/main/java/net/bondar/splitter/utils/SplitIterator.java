package net.bondar.splitter.utils;

import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.splitter.interfaces.Iterable;
import net.bondar.statistics.domain.FilePartObject;

/**
 * Provides iteration for part-files during splitting process.
 */
public class SplitIterator implements Iterable {

    /**
     * Part-file counter.
     */
    private int partCounter = 1;

    /**
     * Current position index.
     */
    private long currentPosition = 0;

    /**
     * The specified file length.
     */
    private long fileLength;

    /**
     * The specified part-file length.
     */
    private long partLength;

    /**
     * Parameter holder.
     */
    private IParameterHolder paramHolder;

    /**
     * Creates <code>SplitIterator</code> instance.
     *
     * @param fileLength the specified file length
     * @param partLength the specified part-file length
     */
    public SplitIterator(IParameterHolder paramHolder, long fileLength, long partLength) {
        this.paramHolder = paramHolder;
        this.fileLength = fileLength;
        this.partLength = partLength;
    }

    /**
     * Gets the next <code>FilePartObject</code> object.
     *
     * @return <code>FilePartObject</code> object with necessary parameters
     */
    public synchronized FilePartObject getNext() {
        if (currentPosition > fileLength) return new FilePartObject();
        long start = currentPosition;
        long end = currentPosition + partLength;
        if (end > fileLength) end = fileLength;
        String partName = paramHolder.getValue("partSuffix") + String.format("%03d", partCounter++);
        FilePartObject filePart = new FilePartObject(partName, start, end, partCounter - 1);
        currentPosition = end + 1;
        return filePart;
    }

}
