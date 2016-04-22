package net.bondar.core.utils;


import net.bondar.core.interfaces.AbstractIteratorFactory;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.Iterable;

import java.io.File;
import java.util.List;

/**
 * Creates concrete file iterators.
 */
public class SplitMergeIteratorFactory implements AbstractIteratorFactory {

    /**
     * Creates <code>SplitIterator</code>.
     *
     * @param fileLength specified file length
     * @param partLength part-file length
     * @return <code>SplitIterator</code> instance
     * @see {@link AbstractIteratorFactory}
     */
    @Override
    public Iterable createSplitIterator(IConfigHolder parameterHolder, long fileLength, long partLength) {
        return new SplitIterator(parameterHolder, fileLength, partLength);
    }

    /**
     * Creates <code>MergeIterator</code>.
     *
     * @param parts list of part-files
     * @return <code>MergeIterator</code> instance
     * @see {@link AbstractIteratorFactory}
     */
    @Override
    public Iterable createMergeIterator(List<File> parts) {
        return new MergeIterator(parts);
    }
}
