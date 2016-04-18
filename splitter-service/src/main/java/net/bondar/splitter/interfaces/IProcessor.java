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
     * Gets the complete file.
     *
     * @return complete file
     */
    File getFile();
}