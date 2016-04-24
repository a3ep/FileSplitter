package net.bondar.splitter;


import net.bondar.core.interfaces.*;
import net.bondar.core.utils.*;
import net.bondar.input.interfaces.*;
import net.bondar.input.service.InputParserService;
import net.bondar.input.utils.*;
import net.bondar.splitter.service.FileService;
import net.bondar.statistics.interfaces.*;
import net.bondar.statistics.service.FileStatisticService;
import net.bondar.statistics.utils.*;
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
            IConfigHolder configHolder = new ApplicationConfigHolder();
            ICommandHolder commandHolder = new CommandHolder();
            ICommandFinder commandFinder = new FileCommandFinder(commandHolder);
            IParameterHolder parameterHolder = new ParameterHolder();
            IParameterFinder parameterFinder = new FileParameterFinder(parameterHolder);
            AbstractConverterFactory converterFactory = new ConverterFactory();
            IParameterParser parameterParser = new ParameterParser(converterFactory);
            ICommandVerifier commandVerifier = new FileCommandVerifier(configHolder);
            IInputParserService parserService = new InputParserService(commandFinder, parameterFinder, parameterParser,
                    commandVerifier);
            IOptionsHolder optionsHolder = new CliOptionsHolder();
            IHelpViewer helpViewer = new HelpViewer(optionsHolder);
            AbstractProcessorFactory processorFactory = new FileProcessorFactory();
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            AbstractTaskFactory taskFactory = new FileTaskFactory();
            AbstractCloseTaskFactory closableFactory = new ApplicationCloseTaskFactory();
            IStatisticHolder statisticHolder = new FileStatisticHolder();
            IStatisticParser statisticParser = new FileStatisticParser();
            IStatisticConverter statisticConverter = new FileStatisticConverter();
            IStatisticBuilder statisticBuilder = new FileStatisticBuilder();
            IStatisticViewer statisticViewer = new FileStatisticViewer();
            IStatisticService statisticService = new FileStatisticService(statisticHolder, statisticParser,
                    statisticConverter, statisticBuilder, statisticViewer);

            new FileService(configHolder, parserService, processorFactory, iteratorFactory, taskFactory, closableFactory,
                    statisticService, helpViewer).run();
        } catch (Throwable t) {
            log.fatal("Unexpected application error. Message: " + t.getMessage());
        }
    }
}
