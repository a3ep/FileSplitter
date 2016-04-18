package net.bondar.user_input.interfaces;

/**
 * Interface for a user input commands.
 */
public interface ICommand {

    /**
     * Gets destination of the specified file.
     *
     * @return string with file destination
     */
    String getFileDestination();

    /**
     * Sets destination of the specified file.
     *
     * @param fileDestination destination of the specified file
     */
    void setFileDestination(String fileDestination);


    /**
     * Gets size of the part-file.
     *
     * @return part-file size
     */
    long getPartSize();

    /**
     * Sets size of the part-file.
     *
     * @param partSize size of the part-file
     */
    void setPartSize(long partSize);
}
