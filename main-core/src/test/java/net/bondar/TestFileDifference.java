package net.bondar;

import net.bondar.calculations.Calculations;
import net.bondar.splitter.interfaces.AbstractIteratorFactory;
import net.bondar.splitter.interfaces.AbstractTaskFactory;
import net.bondar.splitter.interfaces.AbstractThreadFactory;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.splitter.utils.*;
import net.bondar.statistics.FileStatisticFactory;
import net.bondar.statistics.interfaces.AbstractStatisticFactory;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Starts equals files testing.
 */
public class TestFileDifference {
    /**
     * Processor for splitting file.
     */
    private static FileProcessor splitProcessor;

    /**
     * Processor for merging files.
     */
    private static FileProcessor mergeProcessor;

    /**
     * Parameter holder.
     */
    private static IParameterHolder paramHolder;

    /**
     * Name of the part-file.
     */
    private static String partName;

    /**
     * File specified by user.
     */
    private static File specifiedFile;

    /**
     * File received as a result of the merging process.
     */
    private static File resultFile;

    /**
     * Runs preparation for testing.
     */
    @BeforeClass
    public static void setUp() {
        // size of the part-file
        int partSize = 1024 * 1024;
        try {
            // creating a new file with data
            paramHolder = new ApplicationParameterHolder();
            specifiedFile = new File("test.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(specifiedFile));
            while (specifiedFile.length() < 10 * partSize) {
                bw.write("test1 ");
            }
            // initializing processor's components
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            AbstractThreadFactory threadFactory = new NamedThreadFactory();
            AbstractTaskFactory taskFactory = new FileTaskFactory();
            AbstractStatisticFactory statisticFactory = new FileStatisticFactory();
            partName = specifiedFile.getAbsolutePath()+paramHolder.getValue("partSuffix")+"001";
            //creating processors
            splitProcessor = new FileProcessor(specifiedFile.getAbsolutePath(), partSize, paramHolder, iteratorFactory, threadFactory, taskFactory, statisticFactory);
            splitProcessor.process();
            mergeProcessor = new FileProcessor(partName, paramHolder, iteratorFactory, threadFactory, taskFactory, statisticFactory);
            mergeProcessor.process();
            resultFile = mergeProcessor.getFile();
            bw = new BufferedWriter(new FileWriter(resultFile));
            bw.write("test500");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests equals between specified and result files.
     */
    @Test
    public void testFileDifference() {
        specifiedFile = splitProcessor.getFile();
        resultFile = new File("test");
        boolean equals = false;
        //check different file length
        if (specifiedFile.length() != resultFile.length()) {
            equals = false;
        }
        try {
            //comparing contents of files
            equals = FileUtils.contentEquals(specifiedFile, resultFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(false, equals);
    }

    /**
     * Deletes files created during testing.
     */
    @AfterClass
    public static void destroy() {
        specifiedFile.delete();
        List<File> files = Calculations.getPartsList(partName, paramHolder.getValue("partSuffix"));
        for (File file : files) {
            file.delete();
        }
        resultFile.delete();
    }
}
