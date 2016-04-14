package net.bondar.utils;

import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.AbstractProcessorFactory;
import net.bondar.interfaces.IProcessor;

/**
 *
 */
public class SplitMergeProcessorFactory implements AbstractProcessorFactory {

    /**
     * @param fileDest
     * @param iteratorFactory
     * @param partSize
     * @return
     */
    @Override
    public IProcessor createProcessor(String fileDest, AbstractIteratorFactory iteratorFactory, int partSize) {
        if (partSize == 0) {
            return new MergeProcessor(fileDest, iteratorFactory);
        } else {
            return new SplitProcessor(fileDest, partSize, iteratorFactory);
        }
    }
}
