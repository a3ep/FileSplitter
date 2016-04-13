package net.bondar.interfaces;

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
