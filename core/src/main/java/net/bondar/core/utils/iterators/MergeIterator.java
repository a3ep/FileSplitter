package net.bondar.core.utils.iterators;

import net.bondar.core.FilePartObject;
import net.bondar.core.interfaces.Iterable;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides iteration for part-files during merging process.
 */
public class MergeIterator implements Iterable {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

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
    public MergeIterator(List<File> parts) {
        this.parts = new ArrayList<>(parts);
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
