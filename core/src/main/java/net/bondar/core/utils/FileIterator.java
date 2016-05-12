package net.bondar.core.utils;

import net.bondar.core.FilePartObject;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.utils.ConfigHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides iteration for part-files during splitting process.
 */
public class FileIterator implements Iterable {

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
    private ConfigHolder configHolder;

    /**
     * Creates <code>FileIterator</code> instance.
     *
     * @param fileLength the specified file length
     * @param partLength the specified part-file length
     * @see {@link Iterable}
     */
    public FileIterator(ConfigHolder configHolder, long fileLength, long partLength) {
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
