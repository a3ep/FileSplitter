package net.bondar.utils;

import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.AbstractProcessorFactory;
import net.bondar.interfaces.IProcessor;
import net.bondar.interfaces.Iterable;

/**
 *
 */
public class SplitMergeProcessorFactory implements AbstractProcessorFactory{

    /**
     *
     * @param fileDest
     * @param iteratorFactory
     * @param chunkSize
     * @return
     */
    @Override
    public IProcessor createProcessor(String fileDest, AbstractIteratorFactory iteratorFactory, int chunkSize) {
        if(chunkSize==0){
            return new MergeProcessor(fileDest, iteratorFactory);
        }else {
            return new SplitProcessor(fileDest, chunkSize, iteratorFactory);
        }
    }
}
