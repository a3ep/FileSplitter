package net.bondar;


import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.AbstractProcessorFactory;
import net.bondar.interfaces.IParametersParser;
import net.bondar.interfaces.IService;
import net.bondar.utils.CliParameterParser;
import net.bondar.utils.SplitMergeIteratorFactory;
import net.bondar.utils.SplitMergeProcessorFactory;

/**
 *
 */
public class Main {

    public static void main(String[] args) {
        try {
            IParametersParser parametersParser = new CliParameterParser();
            AbstractProcessorFactory processorFactory = new SplitMergeProcessorFactory();
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();

            IService service = new FileService(parametersParser, processorFactory, iteratorFactory);
            service.run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
