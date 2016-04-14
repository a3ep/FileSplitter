package net.bondar.interfaces;

import net.bondar.utils.ReadFileIterator;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.List;

/**
 *
 */
public interface AbstractIteratorFactory {

    /**
     *
     * @param fileLength
     * @param partLength
     * @return
     */
    Iterable createIterator(int fileLength, int partLength);
    Iterable createIterator(List<File> parts);

}
