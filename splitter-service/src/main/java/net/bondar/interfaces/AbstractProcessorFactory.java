package net.bondar.interfaces;

/**
 *
 */
public interface AbstractProcessorFactory {

    /**
     *
     * @param fileDest
     * @param partSize
     * @param parameterHolder
     * @param iteratorFactory
     * @param threadFactory
     * @param runnableFactory
     * @param statisticFactory
     * @return
     */
    IProcessor createProcessor(String fileDest, long partSize,
                               IParameterHolder parameterHolder,
                               AbstractIteratorFactory iteratorFactory,
                               AbstractThreadFactory threadFactory,
                               AbstractRunnableFactory runnableFactory,
                               AbstractStatisticFactory statisticFactory);
}
