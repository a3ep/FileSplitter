package net.bondar.Interface;

/**
 *
 */
public interface AbstractProcessorFactory {

    /**
     *
     * @param fileDest
     * @param iFactory
     * @param chunkSize
     * @return
     */
    IProcessor createProcessor(String fileDest, AbstractIteratorFactory iFactory, String chunkSize);
}
