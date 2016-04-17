package net.bondar;

import net.bondar.interfaces.*;
import net.bondar.service.FileService;
import net.bondar.utils.*;

/**
 *
 */
public class Main {

    public static void main(String[] args) {
        try {
            IParameterHolder parameterHolder = new ApplicationParameterHolder();
            IParametersParser parametersParser = new CliParameterParser(parameterHolder);
            AbstractProcessorFactory processorFactory = new SplitMergeProcessorFactory();
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            AbstractThreadFactory threadFactory = new NamedThreadFactory();
            AbstractTaskFactory runnableFactory = new FileTaskFactory();
            AbstractStatisticFactory statisticFactory = new FileStatisticFactory();

            IService service = new FileService(parameterHolder, parametersParser, processorFactory, iteratorFactory,
                    threadFactory, runnableFactory, statisticFactory);
            service.run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
