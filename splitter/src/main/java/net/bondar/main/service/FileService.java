package net.bondar.main.service;

import net.bondar.calculations.exceptions.CalculationsException;
import net.bondar.main.interfaces.IService;
import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.*;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;
import net.bondar.user_input.domain.Command;
import net.bondar.user_input.interfaces.IParameterParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private final IParameterHolder parameterHolder;

    /**
     * Parameter parser.
     */
    private final IParameterParser parameterParser;

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
     * Statistic factory.
     */
    private final AbstractStatisticFactory statisticFactory;

    /**
     * Interrupt flag.
     */
    private AtomicBoolean interrupt = new AtomicBoolean();

    /**
     * Cleaner thread.
     */
    private final Thread closer;

    /**
     * Creates <code>FileService</code> instance.
     *
     * @param parameterHolder  parameter holder
     * @param parametersParser parameter parser
     * @param processorFactory processor factory
     * @param iteratorFactory  iterator factory
     * @param taskFactory      task factory
     * @param closeTaskFactory closing task factory
     * @param statisticFactory statistic factory
     */
    public FileService(IParameterHolder parameterHolder,
                       IParameterParser parametersParser,
                       AbstractProcessorFactory processorFactory,
                       AbstractIteratorFactory iteratorFactory,
                       AbstractTaskFactory taskFactory,
                       AbstractCloseTaskFactory closeTaskFactory,
                       AbstractStatisticFactory statisticFactory) {
        this.parameterHolder = parameterHolder;
        this.parameterParser = parametersParser;
        this.processorFactory = processorFactory;
        this.iteratorFactory = iteratorFactory;
        this.taskFactory = taskFactory;
        this.closeTaskFactory = closeTaskFactory;
        this.statisticFactory = statisticFactory;
        closer = new Thread(closeTaskFactory.createClosable(interrupt));

    }

    /**
     * Runs communication with user.
     */
    @Override
    public void run() {
        log.debug("Start application.");
        String input;
        while (!interrupt.get()) {
            try {
                log.info("Input your parameters:");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = br.readLine();
                log.debug("Introduced string -> " + input);
                Command inputCommand = parameterParser.parse(input.split(" "));
                switch (inputCommand) {
                    case EMPTY:
                        break;
                    case EXIT:
                        log.debug("Closing resources...");
                        br.close();
                        Runtime.getRuntime().addShutdownHook(closer);
                        System.exit(0);
                        break;
                    case SPLIT:
                        log.debug("Start splitting file -> " + inputCommand.getFileDestination());
                        processorFactory.createProcessor(inputCommand.getFileDestination(),
                                inputCommand.getPartSize(), interrupt, parameterHolder, iteratorFactory, taskFactory,
                                closeTaskFactory, statisticFactory).process();
                        log.debug("Finish splitting file -> " + inputCommand.getFileDestination() + "\n");
                        break;
                    case MERGE:
                        log.debug("Start merging file -> " + inputCommand.getFileDestination());
                        processorFactory.createProcessor(inputCommand.getFileDestination(), 0, interrupt, parameterHolder,
                                iteratorFactory, taskFactory, closeTaskFactory, statisticFactory).process();
                        log.debug("Finish merging file -> " + inputCommand.getFileDestination() + "\n");
                        break;
                }
            } catch (ApplicationException | CalculationsException e) {
                log.warn("File Splitter Application error. Message: " + e.getMessage() + "\n");
            } catch (IOException e) {
                log.warn("Error while processing input. Message " + e.getMessage());
            }
        }
    }
}
