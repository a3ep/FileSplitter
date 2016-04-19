package net.bondar.main;


import net.bondar.main.interfaces.IService;
import net.bondar.main.service.FileService;
import net.bondar.splitter.interfaces.*;
import net.bondar.splitter.utils.*;
import net.bondar.statistics.FileStatisticFactory;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;
import net.bondar.user_input.interfaces.IParameterParser;
import net.bondar.user_input.utils.CliParameterParser;

/**
 * The application starting point.
 */
public class Main {

    public static void main(String[] args) {
        try {
            IParameterHolder paramHolder = new ApplicationParameterHolder();
            IParameterParser paramParser = new CliParameterParser(paramHolder);
            AbstractProcessorFactory processorFactory = new FileProcessorFactory();
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            AbstractThreadFactory threadFactory = new NamedThreadFactory();
            AbstractTaskFactory taskFactory = new FileTaskFactory();
            AbstractStatisticFactory statFactory = new FileStatisticFactory();

            IService service = new FileService(paramHolder, paramParser, processorFactory, iteratorFactory,
                    threadFactory, taskFactory, statFactory);
            service.run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
