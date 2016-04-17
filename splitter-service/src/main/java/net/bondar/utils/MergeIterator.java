package net.bondar.utils;

import net.bondar.domain.FilePart;
import net.bondar.interfaces.Iterable;

import java.io.File;
import java.util.List;

/**
 *
 */
public class MergeIterator implements Iterable {

    /**
     *
     */
    private int counter = 1;

    /**
     *
     */
    private long currentPosition = 0;

    /**
     *
     */
    private List<File> parts;

    /**
     * @param parts
     */
    public MergeIterator(List<File> parts) {
        this.parts = parts;
    }

    /**
     * @return
     */
    @Override
    public synchronized FilePart getNext() {
        if (parts.isEmpty()) {
            return new FilePart();
        }
        long start = currentPosition;
        File part = parts.remove(0);
        long end = start + part.length();
        FilePart filePart = new FilePart(part, start, end, counter++);
        currentPosition += part.length() + 1;
        return filePart;
    }
}
