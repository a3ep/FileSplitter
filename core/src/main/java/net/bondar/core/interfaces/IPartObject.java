package net.bondar.core.interfaces;

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
    String getStatus();

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
     * Gets size of bytes, written by one thread during processing one part-file.
     *
     * @return size of bytes
     */
    long getWrittenSize();

    /**
     * Sets size of bytes, written by one thread during processing one part-file.
     *
     * @param writtenSize size of bytes, written by one thread during processing one part-file
     */
    void setWrittenSize(long writtenSize);

    /**
     * Gets size of bytes, written by one thread during procesing all part-files.
     *
     * @return size of bytes
     */
    long getTotalWrittenSize();

    /**
     * Sets size of bytes, written by one thread during processing all part-files.
     *
     * @param totalWrittenSize size of bytes, written by one thread during processing all part-files
     */
    void setTotalWrittenSize(long totalWrittenSize);

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
