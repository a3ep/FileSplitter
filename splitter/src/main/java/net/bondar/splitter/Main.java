package net.bondar.splitter;


import net.bondar.core.utils.ConfigHolder;
import net.bondar.core.utils.converters.AdvancedStatisticsDataConverter;
import net.bondar.core.utils.factories.CloseTaskFactory;
import net.bondar.core.utils.factories.IteratorFactory;
import net.bondar.core.utils.factories.ProcessorFactory;
import net.bondar.core.utils.factories.TaskFactory;
import net.bondar.input.interfaces.IParserService;
import net.bondar.input.interfaces.client.AbstractParameterConverterFactory;
import net.bondar.input.service.ParserService;
import net.bondar.input.utils.*;
import net.bondar.splitter.service.FileService;
import net.bondar.splitter.utils.Command;
import net.bondar.splitter.utils.FileParameterConverterFactory;
import net.bondar.splitter.utils.Parameter;
import net.bondar.statistics.formatters.DelimiterFormat;
import net.bondar.statistics.formatters.ProgressFormat;
import net.bondar.statistics.formatters.TimerFormat;
import net.bondar.statistics.interfaces.*;
import net.bondar.statistics.interfaces.client.IStatisticsDataConverter;
import net.bondar.statistics.service.StatisticsService;
import net.bondar.statistics.utils.StatisticsHolder;
import net.bondar.statistics.utils.viewers.LogStatisticsViewer;
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
            ConfigHolder configHolder = new ConfigHolder();
            CommandHolder commandHolder = new CommandHolder(Arrays.asList(Command.values()));
            CommandFinder commandFinder = new CommandFinder(commandHolder);
            ParameterHolder parameterHolder = new ParameterHolder(Arrays.asList(Parameter.values()));
            ParameterFinder parameterFinder = new ParameterFinder(parameterHolder);
            AbstractParameterConverterFactory converterFactory = new FileParameterConverterFactory();
            ParameterParser parameterParser = new ParameterParser(parameterFinder, converterFactory);
            IParserService parserService = new ParserService(commandFinder, parameterParser);
            CliOptionsHolder optionsHolder = new CliOptionsHolder(commandHolder, parameterHolder);
            HelpViewer helpViewer = new HelpViewer(optionsHolder);
            ProcessorFactory processorFactory = new ProcessorFactory();
            IteratorFactory iteratorFactory = new IteratorFactory();
            TaskFactory taskFactory = new TaskFactory();
            CloseTaskFactory closableFactory = new CloseTaskFactory();
            StatisticsHolder statisticHolder = new StatisticsHolder();
            IStatisticsDataConverter statisticsDataConverter = new AdvancedStatisticsDataConverter();
            IStatisticsCalculator statisticsCalculator = new AdvancedStatisticsCalculator();
            IStatisticsFormatter statisticsFormatter = new AdvancedStatisticsFormatter(PROGRESS_DESCRIPTION,
                    TIMER_DESCRIPTION, DelimiterFormat.COMMA, DelimiterFormat.COLON, ProgressFormat.PERCENTAGE,
                    TimerFormat.SECONDS);
            IStatisticsViewer statisticsViewer = new LogStatisticsViewer();
            IStatisticsService statisticsService = new StatisticsService(statisticHolder, statisticsDataConverter,
                    statisticsCalculator, statisticsFormatter, statisticsViewer);
            new FileService(configHolder, parserService, processorFactory, iteratorFactory, taskFactory, closableFactory,
                    statisticsService, helpViewer).run();
        } catch (Throwable t) {
            log.fatal("Unexpected application error. Message: " + t.getMessage());
        }
    }
}
