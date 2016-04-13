package net.bondar;

import net.bondar.Interface.AbstractIteratorFactory;
import net.bondar.Interface.AbstractProcessorFactory;
import net.bondar.inretfaces.IParametersParser;
import net.bondar.inretfaces.IService;
import net.bondar.utils.CliParameterParser;
import net.bondar.utils.IteratorFactory;
import net.bondar.utils.SplitMergeProcessorFactory;

/**
 *
 */
public class Main {

    public static void main(String[] args) {
        try {
            IParametersParser parametersParser = new CliParameterParser();
            AbstractProcessorFactory processorFactory = new SplitMergeProcessorFactory();
            AbstractIteratorFactory iteratorFactory = new IteratorFactory();


            IService service = new FileSplitterService(parametersParser, processorFactory, iteratorFactory);
            service.run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
