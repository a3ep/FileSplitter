package net.bondar.interfaces;

import java.io.File;

/**
 *
 */
public interface IPart {

    /**
     * @return
     */
    public String getStatus();

    /**
     * @return
     */
    public String getPartFileName();

    /**
     * @return
     */
    public long getStartPosition();

    /**
     * @return
     */
    public long getEndPosition();

    /**
     *
     * @return
     */
    public long getWrittenSize();

    /**
     *
     * @param writtenSize
     */
    public void setWrittenSize(long writtenSize);

    /**
     *
     * @return
     */
    public long getTotalWrittenSize();

    /**
     *
     * @param totalWrittenSize
     */
    public void setTotalWrittenSize(long totalWrittenSize);
    /**
     * @return
     */
    public int getCounter();

    /**
     * @return
     */
    public File getFile();

    /**
     * @return
     */
    public String getName();
}
