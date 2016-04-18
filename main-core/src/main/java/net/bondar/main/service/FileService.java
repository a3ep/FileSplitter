package net.bondar.main.service;

import net.bondar.calculations.exceptions.CalculationsException;
import net.bondar.main.interfaces.IService;
import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.*;
import net.bondar.splitter.utils.FileProcessor;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;
import net.bondar.user_input.domain.Command;
import net.bondar.user_input.interfaces.IParametersParser;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//split -p /home/vsevolod/test/Howfast.ogg -s 1M
//merge -p /home/vsevolod/test/Howfast.ogg_part_001
//split -p /home/vsevolod/test/200MB.zip -s 5M
//merge -p /home/vsevolod/test/200MB.zip_part_001

/**
 * Provides communication with user.
 */
public class FileService implements IService {

    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(getClass());

    /**
     * Parameter holder.
     */
    private final IParameterHolder paramHolder;

    /**
     * Parameter parser.
     */
    private final IParametersParser paramParser;

    /**
     * Processor factory.
     */
    private final AbstractProcessorFactory pFactory;

    /**
     * Iterator factory.
     */
    private final AbstractIteratorFactory iFactory;

    /**
     * Thread factory.
     */
    private final AbstractThreadFactory tFactory;

    /**
     * Task factory
     */
    private final AbstractTaskFactory taskFactory;

    /**
     * Statistic factory.
     */
    private final AbstractStatisticFactory statFactory;

    /**
     * Reader of the user commands.
     */
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Creates <code>FileService</code> instance.
     *
     * @param paramHolder      parameter holder
     * @param parametersParser parameter parser
     * @param pFactory         processor factory
     * @param iFactory         iterator factory
     * @param tFactory         thread factory
     * @param taskFactory      task factory
     * @param statFactory      statistic factory
     */
    public FileService(IParameterHolder paramHolder,
                       IParametersParser parametersParser,
                       AbstractProcessorFactory pFactory,
                       AbstractIteratorFactory iFactory,
                       AbstractThreadFactory tFactory,
                       AbstractTaskFactory taskFactory,
                       AbstractStatisticFactory statFactory) {
        this.paramHolder = paramHolder;
        this.paramParser = parametersParser;
        this.pFactory = pFactory;
        this.iFactory = iFactory;
        this.tFactory = tFactory;
        this.taskFactory = taskFactory;
        this.statFactory = statFactory;
    }

    /**
     * Runs communication with user.
     */
    @Override
    public void run() {
        log.info("Start application.");
        String input;
        String[] args;
        while (true) {
            try {
                try {
                    log.info("Input your parameters:");
                    input = br.readLine();
                    args = input.split(" ");
                    log.info("Introduced string -> " + input);
                    Command inputCommand = paramParser.parse(args);
                    //checks input command
                    switchForCommand(inputCommand);
                } catch (IOException e) {
                    log.warn("Catches IOException, during processing user input. Message " + e.getMessage());
                    throw new ApplicationException("Error during processing user input. Exception:" + e.getMessage());
                }
            } catch (ApplicationException | CalculationsException e) {
                log.warn("File Splitter Application error. Message: " + e.getMessage()+"\n");
            }
        }
    }

        /**
         * Checks the user input command and performs required action.
         * <br>
         * Depending on the user input line shows help, starts split or merge processing.
         *
         * @param inputCommand the user input command
         * @throws CalculationsException if part-files not found
         * @throws ApplicationException  if exceptions occurring while <code>IProcessor</code> works
         */

    private void switchForCommand(Command inputCommand) throws CalculationsException, ApplicationException {
        FileProcessor processor;
        if (inputCommand == null) return;
        switch (inputCommand) {
            case EXIT:
                log.info("Closing resources...");
                try {
                    br.close();
                } catch (IOException e) {
                    log.warn("Catches IOException, during processing user input. Message " + e.getMessage());
                    throw new ApplicationException("Error during processing user input. Exception:" + e.getMessage());
                }
                log.info("Closing application...");
                System.exit(0);
            case SPLIT:
                log.info("Start splitting file -> " + inputCommand.getFileDestination()
                        .substring(inputCommand.getFileDestination().lastIndexOf("/") + 1));
                processor = pFactory.createProcessor(inputCommand.getFileDestination(),
                        inputCommand.getPartSize(), paramHolder, iFactory, tFactory,
                        taskFactory, statFactory);
                try {
                    processor.process();
                } catch (ApplicationException e) {
                    break;
                }
                log.info("Finish splitting file -> " + inputCommand.getFileDestination()
                        .substring(inputCommand.getFileDestination().lastIndexOf("/") + 1) + "\n");
                break;
            case MERGE:
                log.info("Start merging file -> " + inputCommand.getFileDestination()
                        .substring(inputCommand.getFileDestination()
                                .lastIndexOf("/") + 1, inputCommand.getFileDestination().indexOf(paramHolder.getValue("partSuffix"))));
                processor = pFactory.createProcessor(inputCommand.getFileDestination(), 0, paramHolder,
                        iFactory, tFactory, taskFactory, statFactory);
                processor.process();
                log.info("Finish merging file -> " + inputCommand.getFileDestination()
                        .substring(inputCommand.getFileDestination()
                                .lastIndexOf("/") + 1, inputCommand.getFileDestination().indexOf(paramHolder.getValue("partSuffix"))) + "\n");
                break;
        }
    }
}
