package net.bondar;

import net.bondar.calculations.Calculations;
import net.bondar.splitter.interfaces.*;
import net.bondar.splitter.utils.*;
import net.bondar.statistics.FileStatisticFactory;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;
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
    private static IParameterHolder parameterHolder;

    /**
     *
     */
    private static String partName;
    /**
     *
     */
    private static File firstFile;

    /**
     *
     */
    private static File resultFile;

    /**
     *
     */
    @BeforeClass
    public static void setUp() {
        int partSize = 1024 * 1024;
        try {
            firstFile = new File("test.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(firstFile));
            while (firstFile.length() < 10 * partSize) {
                bw.write("test ");
            }
            bw.close();
            parameterHolder = new ApplicationParameterHolder();
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            AbstractThreadFactory threadFactory = new NamedThreadFactory();
            AbstractTaskFactory runnableFactory = new FileTaskFactory();
            AbstractStatisticFactory statisticFactory = new FileStatisticFactory();
            partName = firstFile.getAbsolutePath() + parameterHolder.getValue("partSuffix")+"001";
            splitProcessor = new SplitProcessor(firstFile.getAbsolutePath(), partSize, parameterHolder, iteratorFactory, threadFactory, runnableFactory, statisticFactory);
            mergeProcessor = new MergeProcessor(partName, parameterHolder, iteratorFactory, threadFactory, runnableFactory, statisticFactory);
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
        List<File> files = Calculations.getPartsList(partName, parameterHolder.getValue("partSuffix"));
        for(File file:files){
            file.delete();
        }
        resultFile.delete();
    }
}
