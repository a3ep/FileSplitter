package net.bondar.domain;

/**
 *
 */
public class Task {
    private final String partFileName;
    private final int startPosition;
    private final int endPosition;

    public Task(String partFileName, int startPosition, int endPosition) {
        this.partFileName = partFileName;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Task() {
        this.partFileName = "";
        this.startPosition = -1;
        this.endPosition = -1;
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
