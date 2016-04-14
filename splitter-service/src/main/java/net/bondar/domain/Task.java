package net.bondar.domain;

import java.io.File;

/**
 *
 */
public class Task {
    private final String status;
    private final String partFileName;
    private final long startPosition;
    private final long endPosition;
    private final int counter;
    private final File file;

    public Task(String partFileName, long startPosition, long endPosition, int counter) {
        this.status = "OK";
        this.partFileName = partFileName;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.counter = counter;
        this.file = null;
    }

    public Task(File file, long startPosition, int counter) {
        this.status = "OK";
        this.partFileName = "";
        this.startPosition = startPosition;
        this.endPosition = 0;
        this.counter = counter;
        this.file = file;
    }

    public Task() {
        this.status = "NULL";
        this.partFileName = "";
        this.startPosition = -1;
        this.endPosition = -1;
        this.counter = -1;
        this.file = null;
    }

    public String getStatus() {
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

    public int getCounter() {
        return counter;
    }

    public File getFile() {
        return file;
    }

    public String getName(){return "Task #";}
}
