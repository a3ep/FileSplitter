package net.bondar.core;

import java.io.File;

/**
 * Contains file parameters for calculations.
 */
public class FilePartObject {

    /**
     * Object status.
     */
    private final PartObjectStatus status;

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
     * Part counter.
     */
    private final int counter;

    /**
     * Part-file.
     */
    private final File partFile;

    /**
     * Size of file.
     */
    private long fileSize;

    /**
     * Creates <code>FilePartObject</code> instance.
     *
     * @param partFileName  part-file name
     * @param startPosition index of the start position in a file
     * @param endPosition   index of the end position in a file
     * @param counter       part counter
     */
    public FilePartObject(final String partFileName, final long startPosition, final long endPosition, final int counter) {
        this.status = PartObjectStatus.OK;
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
    public FilePartObject(final File partFile, final long startPosition, final long endPosition, final int counter) {
        this.status = PartObjectStatus.OK;
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
        this.status = PartObjectStatus.NULL;
        this.partFileName = "";
        this.startPosition = -1;
        this.endPosition = -1;
        this.counter = -1;
        this.partFile = null;
    }

    public PartObjectStatus getStatus() {
        return status;
    }

    public String getPartFileName() {
        return partFileName;
    }

    public long getStartPosition() {
        return startPosition;
    }

    public long getEndPosition() {
        return endPosition;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getCounter() {
        return counter;
    }

    public File getPartFile() {
        return partFile;
    }
}
