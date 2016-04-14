package net.bondar;

import net.bondar.domain.Command;
import net.bondar.exceptions.UserServiceException;
import net.bondar.interfaces.*;
import net.bondar.utils.ApplicationConfigLoader;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 */
public class FileService implements IService {
    //split -p /home/vsevolod/Загрузки/111/SkillsUpWebApp-master.zip -s 1M
    //split -p /home/vsevolod/test/Howfast.ogg -s 1M
    //exit
    //merge -p /home/vsevolod/Загрузки/111/SkillsUpWebApp-master.zip_part_001
    //merge -p /home/vsevolod/test/Howfast.ogg_part_001
    //    private String[] args = new String[]{"exit"};
    //    private String[] args = new String[]{"help"};
    //    private String[] args = new String[]{"split", "-p", "/home/vsevolod/Загрузки/111/SkillsUpWebApp-master.zip", "-s", "1M"};
    //    private String[] args = new String[]{"merge", "-p", "/home/vsevolod/Загрузки/111/SkillsUpWebApp-master.zip_part_001"};
    //    private String[] args = new String[]{"asdgasdg"};
    /**
     *
     */
    private final Logger log = Logger.getLogger("userLogger");

    private IParametersParser parametersParser;

    private AbstractProcessorFactory processorFactory;

    private AbstractIteratorFactory iteratorFactory;


    public FileService(IParametersParser parametersParser, AbstractProcessorFactory processorFactory, AbstractIteratorFactory iteratorFactory) {
        this.parametersParser = parametersParser;
        this.processorFactory = processorFactory;
        this.iteratorFactory = iteratorFactory;
    }

    @Override
    public void run() {
        log.info("Start application.");
        IProcessor processor;
        String input;
        String[] args = {"help"};
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                log.info("Input your parameters:");
                input = br.readLine();

                args = input.split(" ");
                log.info("Introduced string -> " + input);
                Command inputCommand = parametersParser.parse(args);
                switch (inputCommand) {
                    case EXIT:
                        log.info("Closing resources...");
                        br.close();
                        log.info("Closing application...");
                        System.exit(0);
                    case SPLIT:
                        log.info("Start splitting file -> " + inputCommand.getFirstParameter().substring(inputCommand.getFirstParameter().lastIndexOf("/") + 1));
                        processor = processorFactory.createProcessor(inputCommand.getFirstParameter(), iteratorFactory, inputCommand.getSecondParameter());
                        processor.process();
                        log.info("Finish splitting file -> " + inputCommand.getFirstParameter().substring(inputCommand.getFirstParameter().lastIndexOf("/") + 1));
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
            } catch (IOException e) {
                log.warn("Catches IOException, during processing user input. Message " + e.getMessage());
                throw new UserServiceException("Error during processing user input. Exception:" + e.getMessage());
            }
        }
    }
}
