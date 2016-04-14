package net.bondar.domain;

import java.io.File;

/**
 *
 */
public class Task {

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
    public Task(String partFileName, long startPosition, long endPosition, int counter) {
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
    public Task(File file, long startPosition, int counter) {
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
    public Task() {
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
        return "Task #";
    }
}
