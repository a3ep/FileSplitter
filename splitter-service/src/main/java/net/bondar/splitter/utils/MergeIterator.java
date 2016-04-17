package net.bondar.splitter.utils;

import net.bondar.splitter.interfaces.Iterable;
import net.bondar.statistics.domain.FilePartObject;

import java.io.File;
import java.util.List;

/**
 * Provides iteration for part-files during merging process.
 */
public class MergeIterator implements Iterable {

    /**
     * File part counter.
     */
    private int counter = 1;

    /**
     * Current position index.
     */
    private long currentPosition = 0;

    /**
     * List of part-files.
     */
    private List<File> parts;

    /**
     * Creates <code>MergeIterator</code> instance.
     *
     * @param parts list of part-files.
     */
    public MergeIterator(List<File> parts) {
        this.parts = parts;
    }

    /**
     * Gets the next <code>FilePartObject</code> object.
     *
     * @return <code>FilePartObject</code> object with necessary parameters
     */
    @Override
    public synchronized FilePartObject getNext() {
        if (parts.isEmpty()) {
            return new FilePartObject();
        }
        long start = currentPosition;
        File part = parts.remove(0);
        long end = start + part.length();
        FilePartObject filePart = new FilePartObject(part, start, end, counter++);
        currentPosition += part.length() + 1;
        return filePart;
    }
}
