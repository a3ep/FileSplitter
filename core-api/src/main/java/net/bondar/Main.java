package net.bondar;

import net.bondar.Interface.AbstractIteratorFactory;
import net.bondar.Interface.AbstractProcessorFactory;
import net.bondar.Interface.IProcessor;
import net.bondar.utils.IteratorFactory;
import net.bondar.utils.ReadFileIterator;
import net.bondar.utils.SplitMergeProcessorFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class Main {
    private String[] args = new String[]{"split", "-p", "/home/vsevolod/test/How fast.ogg", "-s", "1M"};
//    private String[] args = new String[]{"exit"};
//    private String[] args = new String[]{"help"};
//    private String[] args = new String[]{"merge", "-p", "/home/vsevolod/test/How fast.ogg_part_001"};
//    private String[] args = new String[]{"asdgasdg"};

    private AbstractIteratorFactory iteratorFactory = new IteratorFactory();
    private AbstractProcessorFactory processorFactory = new SplitMergeProcessorFactory();


    public static void main(String[] args) throws IOException {
        Main main = new Main();
        IProcessor processor;
        CliOptionsViewer cliOptionsViewer = new CliOptionsViewer(main.args);
        Command inputCommand = cliOptionsViewer.parse();
        switch (inputCommand) {
            case EXIT:
                System.exit(0);
                break;
            case SPLIT:
                System.out.println("start split");
                processor = main.processorFactory.createProcessor(inputCommand.getFirstParameter(), main.iteratorFactory, inputCommand.getSecondParameter());
                processor.process();
                System.out.println("finish split");
                break;
            case MERGE:
                System.out.println("start merge");
                processor = main.processorFactory.createProcessor(inputCommand.getFirstParameter(), main.iteratorFactory, null);
                processor.process();
                System.out.println("finish merge");
        }
    }
}
