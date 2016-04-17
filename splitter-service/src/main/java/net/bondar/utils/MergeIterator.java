package net.bondar.utils;

import net.bondar.domain.FilePart;
import net.bondar.interfaces.Iterable;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 *
 */
public class MergeIterator implements Iterable {

    /**
     *
     */
    private final Logger log = Logger.getLogger("splitterLogger");
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
        FilePart filePart = new FilePart(part, start, counter++);
        currentPosition += (int) part.length() + 1;
        return filePart;
    }
}
