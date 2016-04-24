package net.bondar.core.interfaces;

import net.bondar.core.domain.FilePartObject;

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
