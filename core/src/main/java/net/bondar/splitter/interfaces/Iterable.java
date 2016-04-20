package net.bondar.splitter.interfaces;

import net.bondar.statistics.domain.FilePartObject;

/**
 * Interface for class that provides iterating.
 */
public interface Iterable {

    /**
     * Gets the next iteration.
     *
     * @return next iteration
     */
    FilePartObject getNext();
}
