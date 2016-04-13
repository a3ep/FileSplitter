package net.bondar.utils;

import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.Iterable;

import java.io.BufferedInputStream;

/**
 *
 */
public class IteratorFactory implements AbstractIteratorFactory {

    /**
     *
     * @param bis
     * @param buffer
     * @return
     */
    @Override
    public Iterable createIterator(BufferedInputStream bis, byte[] buffer) {
        return new ReadFileIterator(bis, buffer);
    }
}
