package net.bondar.user_input.domain;

import net.bondar.user_input.interfaces.ICommand;

/**
 * Contains user input commands.
 */
public enum Command implements ICommand {

    /**
     * Exit command.
     */
    EXIT,

    /**
     * Empty command
     */
    EMPTY,

    /**
     * Split file command.
     */
    SPLIT("", 0),

    /**
     * Merge files command.
     */
    MERGE("");

    /**
     * Creates <code>Command</code> instance.
     *
     * @param fileDestination destination of the specified file
     * @param partSize        size of the part-file
     */
    Command(String fileDestination, long partSize) {
        this.fileDestination = fileDestination;
        this.partSize = partSize;
    }

    /**
     * Creates <code>Command</code> instance.
     *
     * @param fileDestination destination of the specified file
     */
    Command(String fileDestination) {
        this.fileDestination = fileDestination;
    }

    /**
     * Creates <code>Command</code> instance.
     */
    Command() {
    }

    /**
     * Gets destination of the specified file.
     *
     * @return string with file destination
     */
    public String getFileDestination() {
        return fileDestination;
    }

    /**
     * Sets destination of the specified file.
     *
     * @param fileDestination destination of the specified file
     */
    public void setFileDestination(String fileDestination) {
        this.fileDestination = fileDestination;
    }

    /**
     * Gets size of the part-file.
     *
     * @return part-file size
     */
    public long getPartSize() {
        return partSize;
    }

    /**
     * Sets size of the part-file.
     *
     * @param partSize size of the part-file
     */
    public void setPartSize(long partSize) {
        this.partSize = partSize;
    }

    /**
     * Destination of the specified file
     */
    private String fileDestination;

    /**
     * Size of the part-file
     */
    private long partSize;
}
