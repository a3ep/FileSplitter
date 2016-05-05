package net.bondar.core.interfaces;

import net.bondar.core.PartObjectStatus;

import java.io.File;

/**
 * Contains part object.
 */
public interface IPartObject {

    /**
     * Gets <code>IPartObject</code> status.
     *
     * @return status
     */
    PartObjectStatus getStatus();

    /**
     * Gets name of part-file.
     *
     * @return name of part-file
     */
    String getPartFileName();

    /**
     * Gets index of the start position.
     *
     * @return index of the start position
     */
    long getStartPosition();

    /**
     * Gets index of the end position.
     *
     * @return index of the end position
     */
    long getEndPosition();

    /**
     * Gets file size.
     *
     * @return file size
     */
    long getFileSize();

    /**
     * Sets file size.
     *
     * @param fileSize size of file
     */
    void setFileSize(long fileSize);

    /**
     * Gets part-file counter.
     *
     * @return counter
     */
    int getCounter();

    /**
     * Gets part-file.
     *
     * @return part-file
     */
    File getPartFile();
}
