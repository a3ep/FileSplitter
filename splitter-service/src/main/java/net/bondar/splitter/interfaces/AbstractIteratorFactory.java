package net.bondar.splitter.interfaces;

import java.io.File;
import java.util.List;

/**
 * Interface for creating split or merge iterators.
 */
public interface AbstractIteratorFactory {

    /**
     * Creates concrete iterator on the basis of specified file length and part-file length.
     *
     * @param fileLength specified file length
     * @param partLength part-file length
     * @return concrete iterator instance
     */
    Iterable createSplitIterator(IParameterHolder parameterHolder, long fileLength, long partLength);

    /**
     * Creates concrete iterator on the basis of the list of part-files.
     * @param parts list of part-files
     * @return concrete iterator instance
     */
    Iterable createMergeIterator(List<File> parts);
}
