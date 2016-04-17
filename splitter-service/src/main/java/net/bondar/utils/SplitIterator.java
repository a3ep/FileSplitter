package net.bondar.utils;

import net.bondar.domain.FilePart;
import net.bondar.interfaces.IParameterHolder;
import net.bondar.interfaces.Iterable;

/**
 *
 */
public class SplitIterator implements Iterable {
    /**
     *
     */
    private int partCounter = 1;

    /**
     *
     */
    private long currentPosition = 0;

    /**
     *
     */
    private long fileLength;

    /**
     *
     */
    private long partLength;

    private IParameterHolder parameterHolder;

    /**
     * @param fileLength
     * @param partLength
     */
    public SplitIterator(IParameterHolder parameterHolder, long fileLength, long partLength) {
        this.parameterHolder = parameterHolder;
        this.fileLength = fileLength;
        this.partLength = partLength;
    }

    /**
     * @return
     */
    public synchronized FilePart getNext() {
        if (currentPosition > fileLength) return new FilePart();
        long start = currentPosition;
        long end = currentPosition + partLength;
        if (end > fileLength) end = fileLength;
        String partName = parameterHolder.getValue("partSuffix") + String.format("%03d", partCounter++);
        FilePart filePart = new FilePart(partName, start, end, partCounter - 1);
        currentPosition = end + 1;
        return filePart;
    }

}
