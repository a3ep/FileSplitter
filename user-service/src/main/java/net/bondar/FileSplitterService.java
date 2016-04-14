package net.bondar;

import net.bondar.domain.Command;
import net.bondar.interfaces.*;
import net.bondar.interfaces.Iterable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 */
public class FileSplitterService implements IService {
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
    private IParametersParser parametersParser;

    private AbstractProcessorFactory processorFactory;

    private AbstractIteratorFactory iteratorFactory;


    public FileSplitterService(IParametersParser parametersParser, AbstractProcessorFactory processorFactory, AbstractIteratorFactory iteratorFactory) {
        this.parametersParser = parametersParser;
        this.processorFactory = processorFactory;
        this.iteratorFactory = iteratorFactory;
    }

    @Override
    public void run() {
        IProcessor processor;
        String input;
        String[] args = {"help"};
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("Input your parameters:");
                input = br.readLine();
                args = input.split(" ");

                Command inputCommand = parametersParser.parse(args);
                switch (inputCommand) {
                    case EXIT:
                        br.close();
                        System.exit(0);
                    case SPLIT:
                        System.out.println("start split");
                        processor = processorFactory.createProcessor(inputCommand.getFirstParameter(), iteratorFactory, inputCommand.getSecondParameter());
                        processor.process();
                        System.out.println("finish split");
                        break;
                    case MERGE:
                        System.out.println("start merge");
                        processor = processorFactory.createProcessor(inputCommand.getFirstParameter(), iteratorFactory, 0);
                        processor.process();
                        System.out.println("finish merge");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
