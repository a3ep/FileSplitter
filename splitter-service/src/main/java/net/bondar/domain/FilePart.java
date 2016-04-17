package net.bondar.domain;

import net.bondar.interfaces.IPart;

import java.io.File;

/**
 *
 */
public class FilePart implements IPart{

    /**
     *
     */
    private final String status;

    /**
     *
     */
    private final String partFileName;

    /**
     *
     */
    private final long startPosition;

    /**
     *
     */
    private final long endPosition;

    /**
     *
     */
    private long writtenSize;

    /**
     *
     */
    private final int counter;

    /**
     *
     */
    private final File file;

    /**
     * @param partFileName
     * @param startPosition
     * @param endPosition
     * @param counter
     */
    public FilePart(String partFileName, long startPosition, long endPosition, int counter) {
        this.status = "OK";
        this.partFileName = partFileName;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.counter = counter;
        this.file = null;
    }

    /**
     * @param file
     * @param startPosition
     * @param counter
     */
    public FilePart(File file, long startPosition, int counter) {
        this.status = "OK";
        this.partFileName = "";
        this.startPosition = startPosition;
        this.endPosition = 0;
        this.counter = counter;
        this.file = file;
    }

    /**
     *
     */
    public FilePart() {
        this.status = "NULL";
        this.partFileName = "";
        this.startPosition = -1;
        this.endPosition = -1;
        this.counter = -1;
        this.file = null;
    }

    /**
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return
     */
    public String getPartFileName() {
        return partFileName;
    }

    /**
     * @return
     */
    public long getStartPosition() {
        return startPosition;
    }

    /**
     * @return
     */
    public long getEndPosition() {
        return endPosition;
    }

    /**
     *
     * @return
     */
    public long getWrittenSize() {
        return writtenSize;
    }

    /**
     *
     * @param writtenSize
     */
    public void setWrittenSize(long writtenSize) {
        this.writtenSize = writtenSize;
    }

    /**
     * @return
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     * @return
     */
    public String getName() {
        return "FilePart #";
    }
}
