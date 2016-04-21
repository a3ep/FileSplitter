package net.bondar.statistics.domain;

import net.bondar.statistics.interfaces.IPartObject;

import java.io.File;

/**
 * Contains file parameters for calculations.
 */
public class FilePartObject implements IPartObject {

    /**
     * Object status, should be "OK" or "NULL".
     */
    private final String status;

    /**
     * Part-file name.
     */
    private final String partFileName;

    /**
     * Index of the start position in a file.
     */
    private final long startPosition;

    /**
     * Index of the end position in a file.
     */
    private final long endPosition;

    /**
     * Size of bytes, written by one thread during processing one part-file.
     */
    private long writtenSize;

    /**
     * Size of bytes, written by one thread during processing all part-files.
     */
    private long totalWrittenSize;

    /**
     * Part counter.
     */
    private final int counter;

    /**
     * Part-file.
     */
    private final File partFile;

    /**
     * Creates <code>FilePartObject</code> instance.
     *
     * @param partFileName  part-file name
     * @param startPosition index of the start position in a file
     * @param endPosition   index of the end position in a file
     * @param counter       part counter
     */
    public FilePartObject(String partFileName, long startPosition, long endPosition, int counter) {
        this.status = "OK";
        this.partFileName = partFileName;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.counter = counter;
        this.partFile = null;
    }

    /**
     * Creates <code>FilePartObject</code> instance.
     *
     * @param partFile      part-file
     * @param startPosition index of the start position in a file
     * @param endPosition   index of the end position in a file
     * @param counter       part counter
     */
    public FilePartObject(File partFile, long startPosition, long endPosition, int counter) {
        this.status = "OK";
        this.partFileName = "";
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.counter = counter;
        this.partFile = partFile;
    }

    /**
     * Creates <code>FilePartObject</code> NULL instance.
     */
    public FilePartObject() {
        this.status = "NULL";
        this.partFileName = "";
        this.startPosition = -1;
        this.endPosition = -1;
        this.counter = -1;
        this.partFile = null;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getPartFileName() {
        return partFileName;
    }

    @Override
    public long getStartPosition() {
        return startPosition;
    }

    @Override
    public long getEndPosition() {
        return endPosition;
    }

    @Override
    public long getWrittenSize() {
        return writtenSize;
    }

    @Override
    public void setWrittenSize(long writtenSize) {
        this.writtenSize = writtenSize;
    }

    @Override
    public long getTotalWrittenSize() {
        return totalWrittenSize;
    }

    @Override
    public void setTotalWrittenSize(long totalWrittenSize) {
        this.totalWrittenSize = totalWrittenSize;
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public File getPartFile() {
        return partFile;
    }
}
