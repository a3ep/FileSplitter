package net.bondar.Interface;

import java.io.BufferedInputStream;

/**
 *
 */
public interface AbstractIteratorFactory {

    /**
     *
     * @param bis
     * @param buffer
     * @return
     */
    Iterable createIterator(BufferedInputStream bis, byte[] buffer);
}
