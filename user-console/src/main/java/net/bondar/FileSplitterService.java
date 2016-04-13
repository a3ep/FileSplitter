package net.bondar;

import net.bondar.Interface.AbstractIteratorFactory;
import net.bondar.Interface.AbstractProcessorFactory;
import net.bondar.Interface.IProcessor;
import net.bondar.inretfaces.IParametersParser;
import net.bondar.inretfaces.IService;

/**
 *
 */
public class FileSplitterService implements IService{
    private String[] args = new String[]{"exit"};
//    private String[] args = new String[]{"help"};
//    private String[] args = new String[]{"merge", "-p", "/home/vsevolod/test/How fast.ogg_part_001"};
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
        Command inputCommand = parametersParser.parse(args);
        switch (inputCommand) {
            case EXIT:
                System.exit(0);
                break;
            case SPLIT:
                System.out.println("start split");
                processor = processorFactory.createProcessor(inputCommand.getFirstParameter(), iteratorFactory, inputCommand.getSecondParameter());
                processor.process();
                System.out.println("finish split");
                break;
            case MERGE:
                System.out.println("start merge");
                processor = processorFactory.createProcessor(inputCommand.getFirstParameter(), iteratorFactory, null);
                processor.process();
                System.out.println("finish merge");
        }
    }
}
