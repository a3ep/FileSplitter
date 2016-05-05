package net.bondar.core.interfaces;

import net.bondar.core.FilePartObject;

/**
 * Provides iterating.
 */
public interface Iterable {

    /**
     * Gets the next iteration.
     *
     * @return next iteration
     */
    FilePartObject getNext();
}
