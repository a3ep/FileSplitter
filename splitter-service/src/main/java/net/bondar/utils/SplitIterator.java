package net.bondar.utils;

import net.bondar.domain.FilePart;
import net.bondar.interfaces.Iterable;
import org.apache.log4j.Logger;

/**
 *
 */
public class SplitIterator implements Iterable {
    /**
     *
     */
    private final Logger log = Logger.getLogger("splitterLogger");

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

    /**
     * @param fileLength
     * @param partLength
     */
    public SplitIterator(long fileLength, long partLength) {
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
        String partName = "_part_" + String.format("%03d", partCounter++);
        FilePart filePart = new FilePart(partName, start, end, partCounter - 1);
        currentPosition = end + 1;
        return filePart;
    }

}
