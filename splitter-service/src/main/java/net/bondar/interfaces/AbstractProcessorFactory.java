package net.bondar.interfaces;

import net.bondar.utils.ApplicationConfigLoader;

/**
 *
 */
public interface AbstractProcessorFactory {

    /**
     *
     * @param fileDest
     * @param iteratorFactory
     * @param partSize
     * @return
     */
    IProcessor createProcessor(String fileDest, AbstractIteratorFactory iteratorFactory, int partSize);
}
