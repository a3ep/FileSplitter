package net.bondar;

import net.bondar.interfaces.*;
import net.bondar.utils.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

    /**
     *
     */
    private final Logger log = Logger.getLogger("userLogger");

    /**
     *
     */
    private static IProcessor splitProcessor;
    /**
     *
     */
    private static IProcessor mergeProcessor;

    /**
     *
     */
    private static File firstFile;

    /**
     *
     */
    private static File resultFile;

    @BeforeClass
    public static void setUp() {
        int partSize = 1024 * 1024;
        try {
            firstFile = new File("test.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(firstFile));
            while (firstFile.length() < 10 * partSize) {
                bw.write("test");
            }
            bw.close();
            IParameterHolder parameterHolder = new ApplicationParameterHolder();
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            AbstractThreadFactory threadFactory = new NamedThreadFactory();
            AbstractTaskFactory runnableFactory = new FileTaskFactory();
            AbstractStatisticFactory statisticFactory = new FileStatisticFactory();
            splitProcessor = new SplitProcessor(firstFile.getAbsolutePath(), partSize, parameterHolder, iteratorFactory, threadFactory, runnableFactory, statisticFactory);
            mergeProcessor = new MergeProcessor(firstFile.getAbsolutePath() + "_part_001", parameterHolder, iteratorFactory, threadFactory, runnableFactory, statisticFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Before
    public void init() {
        splitProcessor.process();
        mergeProcessor.process();
    }

    /**
     *
     */
    @Test
    public void testFileSplitter() {
        firstFile = splitProcessor.getFile();
        resultFile = mergeProcessor.getFile();
        boolean equals = false;
        if (firstFile.length() != resultFile.length()) {
            equals = false;
        }
        try {
            equals = FileUtils.contentEquals(firstFile, resultFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(true, equals);
    }

    /**
     *
     */
    @AfterClass
    public static void destroy() {
        firstFile.delete();
        List<File> files = Calculations.getPartsList(firstFile.getAbsolutePath() + "_part_001");
        for (File file : files) {
            file.delete();
        }
        resultFile.delete();

    }
}
