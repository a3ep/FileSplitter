package net.bondar.splitter;

import net.bondar.core.interfaces.*;
import net.bondar.core.utils.*;
import net.bondar.input.interfaces.*;
import net.bondar.input.interfaces.client.AbstractParameterConverterFactory;
import net.bondar.input.interfaces.client.ICommandVerifier;
import net.bondar.input.service.InputParserService;
import net.bondar.input.utils.*;
import net.bondar.splitter.domain.Command;
import net.bondar.splitter.domain.Parameter;
import net.bondar.splitter.service.FileService;
import net.bondar.splitter.utils.FileCommandVerifier;
import net.bondar.splitter.utils.FileParameterConverterFactory;
import net.bondar.statistics.domain.DelimiterFormat;
import net.bondar.statistics.domain.ProgressFormat;
import net.bondar.statistics.domain.TimerFormat;
import net.bondar.statistics.interfaces.*;
import net.bondar.statistics.interfaces.client.IAdvancedStatisticsDataConverter;
import net.bondar.statistics.service.StatisticsService;
import net.bondar.statistics.utils.StatisticsHolder;
import net.bondar.statistics.utils.StatisticsViewer;
import net.bondar.statistics.utils.advanced.AdvancedStatisticsCalculator;
import net.bondar.statistics.utils.advanced.AdvancedStatisticsFormatter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * The application starting point.
 */
public class Main {

    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger(Main.class);

    /**
     * String with progress description.
     */
    private static final String PROGRESS_DESCRIPTION = "Total progress";

    /**
     * String with timer description.
     */
    private static final String TIMER_DESCRIPTION = "time remaining";

    public static void main(String[] args) {
        try {
            IConfigHolder configHolder = new ApplicationConfigHolder();
            ICommandHolder commandHolder = new CommandHolder(Arrays.asList(Command.values()));
            ICommandFinder commandFinder = new CommandFinder(commandHolder);
            IParameterHolder parameterHolder = new ParameterHolder(Arrays.asList(Parameter.values()));
            IParameterFinder parameterFinder = new ParameterFinder(parameterHolder);
            AbstractParameterConverterFactory converterFactory = new FileParameterConverterFactory();
            IParameterParser parameterParser = new ParameterParser(parameterFinder, converterFactory);
            ICommandVerifier commandVerifier = new FileCommandVerifier();
            IInputParserService parserService = new InputParserService(commandFinder, parameterParser,
                    commandVerifier);
            IOptionsHolder optionsHolder = new CliOptionsHolder(commandHolder, parameterHolder);
            IHelpViewer helpViewer = new HelpViewer(optionsHolder);
            AbstractProcessorFactory processorFactory = new FileProcessorFactory();
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            AbstractTaskFactory taskFactory = new FileTaskFactory();
            AbstractCloseTaskFactory closableFactory = new ApplicationCloseTaskFactory();
            IStatisticsHolder statisticHolder = new StatisticsHolder();
            IAdvancedStatisticsDataConverter statisticsDataConverter = new FileAdvancedStatisticsDataConverter(statisticHolder);
            IAdvancedStatisticsCalculator statisticsCalculator = new AdvancedStatisticsCalculator(statisticsDataConverter);
            IAdvancedStatisticsFormatter statisticsFormatter = new AdvancedStatisticsFormatter(PROGRESS_DESCRIPTION, TIMER_DESCRIPTION,
                    DelimiterFormat.COMMA, DelimiterFormat.COLON, ProgressFormat.PERCENTAGE, TimerFormat.SECONDS,
                    statisticHolder, statisticsCalculator);
            IStatisticsViewer statisticsViewer = new StatisticsViewer(statisticsFormatter);
            IStatisticsService statisticsService = new StatisticsService(statisticHolder, statisticsViewer);

            new FileService(configHolder, parserService, processorFactory, iteratorFactory, taskFactory, closableFactory,
                    statisticsService, helpViewer).run();
        } catch (Throwable t) {
            log.fatal("Unexpected application error. Message: " + t.getMessage());
        }
    }
}
