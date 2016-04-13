package net.bondar.interfaces;

import net.bondar.utils.ReadFileIterator;

import java.io.BufferedInputStream;

/**
 *
 */
public interface AbstractIteratorFactory {

    /**
     *
     * @param fileLength
     * @param partLength
     * @return
     */
    Iterable createIterator(int fileLength, int partLength);
    ReadFileIterator createIterator(BufferedInputStream bis);

}
