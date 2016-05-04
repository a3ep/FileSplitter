package net.bondar.core.utils.iterators;

import net.bondar.core.FilePartObject;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.Iterable;

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
    private IConfigHolder configHolder;

    /**
     * Creates <code>SplitIterator</code> instance.
     *
     * @param fileLength the specified file length
     * @param partLength the specified part-file length
     * @see {@link Iterable}
     */
    public SplitIterator(IConfigHolder configHolder, long fileLength, long partLength) {
        this.configHolder = configHolder;
        this.fileLength = fileLength;
        this.partLength = partLength;
    }

    /**
     * Gets the next <code>FilePartObject</code> object.
     *
     * @return <code>FilePartObject</code> object with necessary parameters
     * @see {@link Iterable}
     */
    public synchronized FilePartObject getNext() {
        if (currentPosition > fileLength) return new FilePartObject();
        long start = currentPosition;
        long end = currentPosition + partLength;
        if (end > fileLength) end = fileLength;
        String partName = configHolder.getValue("partSuffix") + String.format("%03d", partCounter++);
        FilePartObject filePart = new FilePartObject(partName, start, end, partCounter - 1);
        filePart.setFileSize(fileLength);
        currentPosition = end + 1;
        return filePart;
    }
}
