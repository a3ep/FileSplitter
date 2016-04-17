package net.bondar.main;


import net.bondar.main.interfaces.IService;
import net.bondar.main.service.FileService;
import net.bondar.splitter.interfaces.*;
import net.bondar.splitter.utils.*;
import net.bondar.statistics.FileStatisticFactory;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;
import net.bondar.user_input.interfaces.IParametersParser;
import net.bondar.user_input.utils.CliParameterParser;

/**
 * The application starting point.
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
