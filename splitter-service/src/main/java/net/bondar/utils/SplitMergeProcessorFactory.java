package net.bondar.utils;

import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.AbstractProcessorFactory;
import net.bondar.interfaces.IProcessor;

/**
 *
 */
public class SplitMergeProcessorFactory implements AbstractProcessorFactory{

    /**
     *
     * @param fileDest
     * @param iFactory
     * @param chunkSize
     * @return
     */
    @Override
    public IProcessor createProcessor(String fileDest, AbstractIteratorFactory iFactory, String chunkSize) {
        if(chunkSize==null){
            return new MergeProcessor(fileDest, iFactory);
        }else {
            return new SplitProcessor(fileDest, chunkSize, iFactory);
        }
    }
}
