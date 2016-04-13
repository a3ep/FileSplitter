package net.bondar.utils;

import net.bondar.Interface.AbstractIteratorFactory;
import net.bondar.Interface.AbstractProcessorFactory;
import net.bondar.Interface.IProcessor;

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
