package net.bondar.service;

import net.bondar.domain.Command;
import net.bondar.exceptions.ApplicationException;
import net.bondar.interfaces.*;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 */
public class FileService implements IService {
    //split -p /home/vsevolod/test/Howfast.ogg -s 1M
    //split -p /home/vsevolod/Загрузки/111/Modern.Family.S07E18.rus.LostFilm.TV.avi -s 20M
    //exit
    //merge -p /home/vsevolod/test/Howfast.ogg_part_001
    //merge -p /home/vsevolod/Загрузки/111/Modern.Family.S07E18.rus.LostFilm.TV.avi_part_001
    /**
     *
     */
    private final Logger log = Logger.getLogger("userLogger");

    /**
     *
     */
    private final IParametersParser parametersParser;

    /**
     *
     */
    private final AbstractProcessorFactory processorFactory;

    /**
     *
     */
    private final AbstractIteratorFactory iteratorFactory;

    /**
     *
     */
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * @param parametersParser
     * @param processorFactory
     * @param iteratorFactory
     */
    public FileService(IParametersParser parametersParser, AbstractProcessorFactory processorFactory, AbstractIteratorFactory iteratorFactory) {
        this.parametersParser = parametersParser;
        this.processorFactory = processorFactory;
        this.iteratorFactory = iteratorFactory;
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            log.info("Start application.");
            String input;
            String[] args = {"help"};
            while (true) {
                try {
                    log.info("Input your parameters:");
                    input = br.readLine();
                    args = input.split(" ");
                    log.info("Introduced string -> " + input);
                    Command inputCommand = parametersParser.parse(args);
                    //checks input command
                    switchForCommand(inputCommand);
                } catch (IOException e) {
                    log.warn("Catches IOException, during processing user input. Message " + e.getMessage());
                    throw new ApplicationException("Error during processing user input. Exception:" + e.getMessage());
                }
            }
        } catch (ApplicationException e) {
            log.warn("File Splitter Application error. Message " + e.getMessage());
        }
    }

    /**
     * @param inputCommand
     */
    private void switchForCommand(Command inputCommand) {
        IProcessor processor;
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
                log.info("Start splitting file -> " + inputCommand.getFirstParameter()
                        .substring(inputCommand.getFirstParameter().lastIndexOf("/") + 1));
                processor = processorFactory.createProcessor(inputCommand.getFirstParameter(),
                        iteratorFactory, inputCommand.getSecondParameter());
                processor.process();
                log.info("Finish splitting file -> " + inputCommand.getFirstParameter()
                        .substring(inputCommand.getFirstParameter().lastIndexOf("/") + 1));
                break;
            case MERGE:
                log.info("Start merging file -> " + inputCommand.getFirstParameter()
                        .substring(inputCommand.getFirstParameter()
                                .lastIndexOf("/") + 1, inputCommand.getFirstParameter().indexOf("_")));
                processor = processorFactory.createProcessor(inputCommand.getFirstParameter(), iteratorFactory, 0);
                processor.process();
                log.info("Finish merging file -> " + inputCommand.getFirstParameter()
                        .substring(inputCommand.getFirstParameter()
                                .lastIndexOf("/") + 1, inputCommand.getFirstParameter().indexOf("_")));
                break;
        }
    }
}
