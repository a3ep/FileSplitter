package net.bondar.core.interfaces;

import net.bondar.core.utils.ProcessorStatus;

import java.io.File;

/**
 * Provides file processing.
 */
public interface IProcessor {

    /**
     * Processes file.
     *
     * @return true if the file processed successfully, false otherwise
     */
    boolean process();

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
     * Gets processor status.
     *
     * @return current processor status
     */
    ProcessorStatus getProcessorStatus();

}
