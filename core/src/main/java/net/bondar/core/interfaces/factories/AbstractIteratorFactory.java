package net.bondar.core.interfaces.factories;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.Iterable;

import java.io.File;
import java.util.List;

/**
 * Provides creating iterators.
 */
public interface AbstractIteratorFactory {

    /**
     * Creates concrete iterator on the basis of specified file length and part-file length.
     *
     * @param fileLength specified file length
     * @param partLength part-file length
     * @return concrete iterator instance
     */
    Iterable createIterator(IConfigHolder parameterHolder, final long fileLength, final long partLength);

    /**
     * Creates concrete iterator on the basis of the list of part-files.
     *
     * @param parts list of part-files
     * @return concrete iterator instance
     */
    Iterable createIterator(final List<File> parts);
}

