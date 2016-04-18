package net.bondar.statistics.interfaces;

import java.io.File;

/**
 * Interface for part objects.
 */
public interface IPartObject {

    /**
     * Gets <code>IPartObject</code> status.
     *
     * @return status
     */
    public String getStatus();

    /**
     * Gets name of part-file.
     *
     * @return name of part-file
     */
    public String getPartFileName();

    /**
     * Gets index of the start position.
     *
     * @return index of the start position
     */
    public long getStartPosition();

    /**
     * Gets index of the end position.
     *
     * @return index of the end position
     */
    public long getEndPosition();

    /**
     * Gets size of bytes, written by one thread during processing one part-file.
     *
     * @return size of bytes
     */
    public long getWrittenSize();

    /**
     * Sets size of bytes, written by one thread during processing one part-file.
     *
     * @param writtenSize size of bytes, written by one thread during processing one part-file
     */
    public void setWrittenSize(long writtenSize);

    /**
     * Gets size of bytes, written by one thread during procesing all part-files.
     *
     * @return size of bytes
     */
    public long getTotalWrittenSize();

    /**
     * Sets size of bytes, written by one thread during processing all part-files.
     *
     * @param totalWrittenSize size of bytes, written by one thread during processing all part-files
     */
    public void setTotalWrittenSize(long totalWrittenSize);

    /**
     * Gets part-file counter.
     *
     * @return counter
     */
    public int getCounter();

    /**
     * Gets part-file.
     *
     * @return part-file
     */
    public File getPartFile();

    /**
     * Gets <code>IPartObject</code> name.
     *
     * @return name
     */
    public String getName();
}
