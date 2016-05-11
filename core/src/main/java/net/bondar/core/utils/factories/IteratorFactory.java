package net.bondar.core.utils.factories;


import net.bondar.core.interfaces.Iterable;
import net.bondar.core.utils.ConfigHolder;
import net.bondar.core.utils.iterators.MergeIterator;
import net.bondar.core.utils.iterators.SplitIterator;

import java.io.File;
import java.util.List;

/**
 * Creates concrete file iterators.
 */
public class IteratorFactory {

    /**
     * Creates <code>SplitIterator</code>.
     *
     * @param configHolder configuration holder
     * @param fileLength   specified file length
     * @param partLength   part-file length
     * @return <code>SplitIterator</code> instance
     */
    public Iterable createIterator(ConfigHolder configHolder, final long fileLength, final long partLength) {
        return new SplitIterator(configHolder, fileLength, partLength);
    }

    /**
     * Creates <code>MergeIterator</code>.
     *
     * @param parts list of part-files
     * @return <code>MergeIterator</code> instance
     */
    public Iterable createIterator(final List<File> parts) {
        return new MergeIterator(parts);
    }
}
