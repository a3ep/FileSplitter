package net.bondar.core.utils.factories;


import net.bondar.core.interfaces.AbstractIteratorFactory;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.Iterable;
import net.bondar.core.utils.iterators.MergeIterator;
import net.bondar.core.utils.iterators.SplitIterator;

import java.io.File;
import java.util.List;

/**
 * Creates concrete file iterators.
 */
public class IteratorFactory implements AbstractIteratorFactory {

    /**
     * Creates <code>SplitIterator</code>.
     *
     * @param fileLength specified file length
     * @param partLength part-file length
     * @return <code>SplitIterator</code> instance
     * @see {@link AbstractIteratorFactory}
     */
    @Override
    public Iterable createIterator(IConfigHolder parameterHolder, long fileLength, long partLength) {
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
    public Iterable createIterator(List<File> parts) {
        return new MergeIterator(parts);
    }
}
