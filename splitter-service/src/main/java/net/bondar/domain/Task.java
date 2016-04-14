package net.bondar.domain;

/**
 *
 */
public class Task {
    private final String status;
    private final String partFileName;
    private final int startPosition;
    private final int endPosition;

    public Task(String partFileName, int startPosition, int endPosition) {
        this.status = "OK";
        this.partFileName = partFileName;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Task() {
        this.status = "NULL";
        this.partFileName = "";
        this.startPosition = -1;
        this.endPosition = -1;
    }

    public String getStatus() {
        return status;
    }

    public String getPartFileName() {
        return partFileName;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }
}
