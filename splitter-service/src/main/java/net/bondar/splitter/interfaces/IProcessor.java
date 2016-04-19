package net.bondar.splitter.interfaces;

import java.io.File;

/**
 * Interface for class that provides file processing.
 */
public interface IProcessor {

    /**
     * Processes file.
     */
    void process();

    /**
     * Gets flag for interrupting working threads.
     *
     * @return interrupt flag
     */
    boolean getInterrupt();

    /**
     * Sets flag for interrupting working threads.
     *
     * @param value interrupt flag value
     */
    void setInterrupt(boolean value);

    /**
     * Gets current process status.
     *
     * @return process status
     */
    String getProcessStatus();

    /**
     * Sets process status.
     *
     * @param status process status, should be "OK" or "ERROR"
     */
    void setProcessStatus(String status);

    /**
     * Gets specified file operation name.
     *
     * @return file operation name
     */
    String getFileOperation();

    /**
     * Gets the complete file.
     *
     * @return complete file
     */
    File getFile();
}
