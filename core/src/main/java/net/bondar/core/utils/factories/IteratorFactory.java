package net.bondar.core.utils.factories;


import net.bondar.core.interfaces.Iterable;
import net.bondar.core.utils.ConfigHolder;
import net.bondar.core.utils.FileIterator;

/**
 * Creates concrete file iterators.
 */
public class IteratorFactory {

    /**
     * Creates <code>FileIterator</code>.
     *
     * @param configHolder configuration holder
     * @param fileLength   specified file length
     * @param partLength   part-file length
     * @return <code>FileIterator</code> instance
     */
    public Iterable createIterator(ConfigHolder configHolder, final long fileLength, final long partLength) {
        return new FileIterator(configHolder, fileLength, partLength);
    }
}
