package net.bondar.interfaces;

import java.io.File;
import java.util.List;

/**
 *
 */
public interface AbstractIteratorFactory {

    /**
     * @param fileLength
     * @param partLength
     * @return
     */
    Iterable createIterator(IParameterHolder parameterHolder, long fileLength, long partLength);

    Iterable createIterator(List<File> parts);

}
