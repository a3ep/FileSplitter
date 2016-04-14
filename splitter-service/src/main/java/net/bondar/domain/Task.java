package net.bondar.domain;

/**
 *
 */
public class Task {
    private final String status;
    private final String partFileName;
    private final int startPosition;
    private final int endPosition;
    private final int counter;

    public Task(String partFileName, int startPosition, int endPosition, int counter) {
        this.status = "OK";
        this.partFileName = partFileName;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.counter = counter;
    }

    public Task() {
        this.status = "NULL";
        this.partFileName = "";
        this.startPosition = -1;
        this.endPosition = -1;
        this.counter = -1;
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

    public int getCounter() {
        return counter;
    }

    public String getName(){return "Task #";}
}
