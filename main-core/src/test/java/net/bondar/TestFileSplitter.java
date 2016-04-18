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
 * Provides application testing.
 */
public class TestFileSplitter {

    /**
     * Logger.
     */
    private static final Logger log = Logger.getLogger(TestFileSplitter.class);

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
            specifiedFile = new File("test.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(specifiedFile));
            while (specifiedFile.length() < 10 * partSize) {
                bw.write("test ");
            }
            bw.close();
            paramHolder = new ApplicationParameterHolder();
            // initializing processor's components
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            AbstractThreadFactory threadFactory = new NamedThreadFactory();
            AbstractTaskFactory runnableFactory = new FileTaskFactory();
            AbstractStatisticFactory statisticFactory = new FileStatisticFactory();
            partName = specifiedFile.getAbsolutePath() + paramHolder.getValue("partSuffix") + "001";
            //creating processors
            splitProcessor = new FileProcessor(specifiedFile.getAbsolutePath(), partSize, paramHolder, iteratorFactory, threadFactory, runnableFactory, statisticFactory);
            mergeProcessor = new FileProcessor(partName, paramHolder, iteratorFactory, threadFactory, runnableFactory, statisticFactory);
        } catch (IOException e) {
            log.error("Catches IOException, during creating file for testing. Message " + e.getMessage());
        }
    }

    /**
     * Starts split and merge processes.
     */
    @Before
    public void init() {
        splitProcessor.process();
        mergeProcessor.process();
    }

    /**
     * Tests equals between specified and result files.
     */
    @Test
    public void testFilesEquals() {
        specifiedFile = splitProcessor.getFile();
        resultFile = mergeProcessor.getFile();
        boolean equals = false;
        //check different file length
        if (specifiedFile.length() != resultFile.length()) {
            equals = false;
        }
        try {
            //comparing contents of files
            equals = FileUtils.contentEquals(specifiedFile, resultFile);
        } catch (IOException e) {
            log.error("Catches IOException, during comparing contents of files. Message " + e.getMessage());
        }
        assertEquals(true, equals);
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
