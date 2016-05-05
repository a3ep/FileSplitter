package net.bondar.splitter.service;

import net.bondar.calculations.exceptions.CalculationsException;
import net.bondar.core.exceptions.RunException;
import net.bondar.core.interfaces.*;
import net.bondar.core.interfaces.factories.AbstractCloseTaskFactory;
import net.bondar.core.interfaces.factories.AbstractIteratorFactory;
import net.bondar.core.interfaces.factories.AbstractProcessorFactory;
import net.bondar.core.interfaces.factories.AbstractTaskFactory;
import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.IHelpViewer;
import net.bondar.input.interfaces.IInputParserService;
import net.bondar.splitter.utils.Command;
import net.bondar.splitter.interfaces.IService;
import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.IStatisticsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Provides communication with user.
 */
public class FileService implements IService {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Parameter holder.
     */
    private final IConfigHolder parameterHolder;

    /**
     * Input parser service.
     */
    private final IInputParserService parserService;

    /**
     * Processor factory.
     */
    private final AbstractProcessorFactory processorFactory;

    /**
     * Iterator factory.
     */
    private final AbstractIteratorFactory iteratorFactory;

    /**
     * Task factory
     */
    private final AbstractTaskFactory taskFactory;

    /**
     * Closing task factory.
     */
    private final AbstractCloseTaskFactory closeTaskFactory;

    /**
     * Statistic service.
     */
    private final IStatisticsService statisticsService;

    /**
     * Help viewer.
     */
    private final IHelpViewer helpViewer;

    /**
     * Creates <code>FileService</code> instance.
     *
     * @param parameterHolder   parameter holder
     * @param parserService     input parser service
     * @param processorFactory  processor factory
     * @param iteratorFactory   iterator factory
     * @param taskFactory       task factory
     * @param closeTaskFactory  closing task factory
     * @param statisticsService statistics service
     * @param helpViewer        help viewer
     */
    public FileService(IConfigHolder parameterHolder,
                       IInputParserService parserService,
                       AbstractProcessorFactory processorFactory,
                       AbstractIteratorFactory iteratorFactory,
                       AbstractTaskFactory taskFactory,
                       AbstractCloseTaskFactory closeTaskFactory,
                       IStatisticsService statisticsService,
                       IHelpViewer helpViewer) {
        this.parameterHolder = parameterHolder;
        this.parserService = parserService;
        this.processorFactory = processorFactory;
        this.iteratorFactory = iteratorFactory;
        this.taskFactory = taskFactory;
        this.closeTaskFactory = closeTaskFactory;
        this.statisticsService = statisticsService;
        this.helpViewer = helpViewer;
    }

    /**
     * Runs communication with user.
     */
    @Override
    public void run() {
        log.debug("Start application.");
        String input;
        while (true) {
            try {
                log.info("Input your parameters:");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = br.readLine();
                log.debug("Introduced string -> " + input);
                Command inputCommand = (Command) parserService.parse(input.split(" "));
                switch (inputCommand) {
                    case HELP:
                        helpViewer.showHelp();
                        break;
                    case EXIT:
                        log.debug("Closing resources...");
                        br.close();
                        log.debug("Application closed.");
                        System.exit(0);
                        break;
                    case SPLIT:
                        log.debug("Start splitting file -> " + inputCommand.getParameters().get(0).getValue());
                        processorFactory.createProcessor(inputCommand.getParameters().get(0).getValue(),
                                Long.parseLong(inputCommand.getParameters().get(1).getValue()),
                                parameterHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticsService,
                                inputCommand.name()).process();
                        log.debug("Finish splitting file -> " + inputCommand.getParameters().get(0).getValue() + "\n");
                        break;
                    case MERGE:
                        log.debug("Start merging file -> " + inputCommand.getParameters().get(0).getValue());
                        processorFactory.createProcessor(inputCommand.getParameters().get(0).getValue(), 0,
                                parameterHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticsService,
                                inputCommand.name()).process();
                        log.debug("Finish merging file -> " + inputCommand.getParameters().get(0).getValue() + "\n");
                        break;
                }
            } catch (RunException | CalculationsException | ParsingException | StatisticsException e) {
                log.warn("File Splitter Application error. Message: " + e.getMessage() + "\n");
            } catch (IOException e) {
                log.warn("Error while processing input. Message " + e.getMessage());
            }
        }
    }
}
