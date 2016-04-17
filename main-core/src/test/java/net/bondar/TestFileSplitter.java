package net.bondar;

import net.bondar.interfaces.*;
import net.bondar.utils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


/**
 *
 */
public class TestFileSplitter {
    private static IProcessor splitProcessor;
    private static IProcessor mergeProcessor;
    private static File resultFile;

    /**
     *
     */
    @Before
    public void init() {
        int partSize = 1024 * 1024;
        try {
            File file = new File("test.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            while (file.length() < 10 * partSize) {
                bw.write(1);
            }
            bw.close();
            IParameterHolder parameterHolder = new ApplicationParameterHolder();
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            AbstractThreadFactory threadFactory = new NamedThreadFactory();
            AbstractRunnableFactory runnableFactory = new FileRunnableFactory();
            AbstractStatisticFactory statisticFactory = new FileStatisticFactory();
            splitProcessor = new SplitProcessor(file.getAbsolutePath(), partSize, parameterHolder, iteratorFactory, threadFactory, runnableFactory, statisticFactory);
            splitProcessor.process();
            mergeProcessor = new MergeProcessor(file.getAbsolutePath() + "_part_001", parameterHolder, iteratorFactory, threadFactory, runnableFactory, statisticFactory);
            mergeProcessor.process();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     */
    @Test
    public void testFileSplitter() {
        resultFile = mergeProcessor.getFile();
        assertEquals(splitProcessor.getFile().length(), resultFile.length());
    }

    /**
     *
     */
    @After
    public void destroy() {
        resultFile.delete();
    }
}
