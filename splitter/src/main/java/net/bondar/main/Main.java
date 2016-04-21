package net.bondar.main;


import net.bondar.main.service.FileService;
import net.bondar.splitter.interfaces.*;
import net.bondar.splitter.utils.*;
import net.bondar.statistics.FileStatisticFactory;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;
import net.bondar.user_input.interfaces.IParameterParser;
import net.bondar.user_input.utils.CliParameterParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The application starting point.
 */
public class Main {

    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            IParameterHolder paramHolder = new ApplicationParameterHolder();
            IParameterParser paramParser = new CliParameterParser(paramHolder);
            AbstractProcessorFactory processorFactory = new FileProcessorFactory();
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            AbstractTaskFactory taskFactory = new FileTaskFactory();
            AbstractCloseTaskFactory closableFactory = new ApplicationCloseTaskFactory();
            AbstractStatisticFactory statFactory = new FileStatisticFactory();

            new FileService(paramHolder, paramParser, processorFactory, iteratorFactory, taskFactory, closableFactory, statFactory).run();
        } catch (Throwable t) {
            log.fatal("Unexpected application error. Message: " + t.getMessage());
        }
    }
}
