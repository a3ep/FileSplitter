package net.bondar.splitter.service;

import net.bondar.core.exceptions.RunException;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.IProcessor;
import net.bondar.core.interfaces.factories.AbstractCloseTaskFactory;
import net.bondar.core.interfaces.factories.AbstractIteratorFactory;
import net.bondar.core.interfaces.factories.AbstractProcessorFactory;
import net.bondar.core.interfaces.factories.AbstractTaskFactory;
import net.bondar.core.utils.FilesFinder;
import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.IParserService;
import net.bondar.input.utils.HelpViewer;
import net.bondar.splitter.interfaces.IService;
import net.bondar.splitter.utils.Command;
import net.bondar.statistics.exceptions.StatisticsException;
import net.bondar.statistics.interfaces.IStatisticsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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
    private final IConfigHolder configHolder;

    /**
     * Input parser service.
     */
    private final IParserService parserService;

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
    private final HelpViewer helpViewer;

    /**
     * Input command.
     */
    private Command inputCommand = Command.EMPTY;

    /**
     * Current processor.
     */
    private IProcessor processor;

    /**
     * Creates <code>FileService</code> instance.
     *
     * @param configHolder   parameter holder
     * @param parserService     input parser service
     * @param processorFactory  processor factory
     * @param iteratorFactory   iterator factory
     * @param taskFactory       task factory
     * @param closeTaskFactory  closing task factory
     * @param statisticsService statistics service
     * @param helpViewer        help viewer
     */
    public FileService(IConfigHolder configHolder,
                       IParserService parserService,
                       AbstractProcessorFactory processorFactory,
                       AbstractIteratorFactory iteratorFactory,
                       AbstractTaskFactory taskFactory,
                       AbstractCloseTaskFactory closeTaskFactory,
                       IStatisticsService statisticsService,
                       HelpViewer helpViewer) {
        this.configHolder = configHolder;
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
        log.info("Start application.");
        String input;
        while (!inputCommand.equals(Command.EXIT)) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = br.readLine();
                log.debug("Introduced string -> " + input);
                inputCommand = (Command) parserService.parse(input.split(" "));
                switch (inputCommand) {
                    case HELP:
                        helpViewer.showHelp();
                        break;
                    case EXIT:
                        log.debug("Closing resources...");
                        br.close();
                        log.debug("Application closed.");
                        break;
                    case SPLIT:
                        log.debug("Start splitting file -> " + inputCommand.getParameters().get(0).getValue());
                        processor = processorFactory.createProcessor(inputCommand.getParameters().get(0).getValue(),
                                Long.parseLong(inputCommand.getParameters().get(1).getValue()),
                                configHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticsService,
                                inputCommand.name());
                        if (!processor.process()) {
                            close();
                            break;
                        }
                        log.debug("Finish splitting file -> " + inputCommand.getParameters().get(0).getValue() + "\n");
                        break;
                    case MERGE:
                        log.debug("Start merging file -> " + inputCommand.getParameters().get(0).getValue());
                        processor = processorFactory.createProcessor(inputCommand.getParameters().get(0).getValue(), 0,
                                configHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticsService,
                                inputCommand.name());
                        if (!processor.process()) {
                            close();
                            break;
                        }
                        log.debug("Finish merging file -> " + inputCommand.getParameters().get(0).getValue() + "\n");
                        break;
                }
            } catch (RunException | ParsingException | StatisticsException e) {
                log.error("File Splitter Application error. Message: " + e.getMessage() + "\n");
            } catch (IOException e) {
                log.error("Error while processing user input. Message " + e.getMessage());
            }
        }
    }

    /**
     * Cleans temporary files.
     *
     * @param currentCommand current input command
     * @param temporaryFile temporary file
     */
    private void cleanTemporaryFiles(Command currentCommand, File temporaryFile){
        log.debug("Start cleaning temporary files...");
        try {
            if (currentCommand.name().equalsIgnoreCase(Command.SPLIT.name())) {
                List<File> files = FilesFinder.getPartsList(temporaryFile.getAbsolutePath(), configHolder.getValue("partSuffix"));
                log.debug("Deleting temporary files.");
                files.forEach(File::delete);
            } else {
                log.debug("Deleting temporary file: " + temporaryFile.getAbsolutePath());
                temporaryFile.delete();
            }
        } catch (SecurityException e) {
            log.warn("Error while cleaning temporary files. Message: " + e.getMessage());
        }
        log.debug("Finish cleaning temporary files.");
    }

    /**
     * Initiates closing application.
     */
    private void close(){
        cleanTemporaryFiles(inputCommand, processor.getFile());
        inputCommand = Command.EXIT;
    }
}
