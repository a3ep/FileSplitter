package net.bondar.core.interfaces;

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
     * Gets the complete file.
     *
     * @return complete file
     */
    File getFile();

    /**
     * Gets name of executing command.
     *
     * @return name of executing command
     */
    String getCommandName();

    /**
     * Gets process status.
     *
     * @return current process status
     */
    String getProcessStatus();
}
