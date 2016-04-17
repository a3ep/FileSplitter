package net.bondar.utils;


import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.Iterable;

import java.io.File;
import java.util.List;

/**
 *
 */
public class SplitMergeIteratorFactory implements AbstractIteratorFactory {

    /**
     * @param fileLength
     * @param partLength
     * @return
     */
    @Override
    public Iterable createIterator(int fileLength, int partLength) {
        return new SplitIterator(fileLength, partLength);
    }

    /**
     * @param parts
     * @return
     */
    @Override
    public Iterable createIterator(List<File> parts) {
        return new MergeIterator(parts);
    }
}
