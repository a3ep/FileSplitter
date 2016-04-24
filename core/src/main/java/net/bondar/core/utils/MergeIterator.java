package net.bondar.core.utils;

import net.bondar.core.domain.FilePartObject;
import net.bondar.core.interfaces.Iterable;

import java.io.File;
import java.util.List;

/**
 * Provides iteration for part-files during merging process.
 */
class MergeIterator implements Iterable {

    /**
     * File part counter.
     */
    private int counter = 1;

    /**
     * Current position index.
     */
    private long currentPosition = 0;

    /**
     * Size of complete file.
     */
    private long completeFileSize = 0;

    /**
     * List of part-files.
     */
    private List<File> parts;

    /**
     * Creates <code>MergeIterator</code> instance.
     *
     * @param parts list of part-files.
     * @see {@link Iterable}
     */
    MergeIterator(List<File> parts) {
        this.parts = parts;
        for (File f : parts) {
            completeFileSize += f.length();
        }
    }

    /**
     * Gets the next <code>FilePartObject</code> object.
     *
     * @return <code>FilePartObject</code> object with necessary parameters
     * @see {@link Iterable}
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
        filePart.setFileSize(completeFileSize);
        currentPosition += part.length() + 1;
        return filePart;
    }
}
