package net.bondar.test.processor;

import net.bondar.calculations.Calculations;
import net.bondar.splitter.utils.ApplicationParameterHolder;
import net.bondar.splitter.interfaces.AbstractIteratorFactory;
import net.bondar.splitter.interfaces.AbstractTaskFactory;
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
 * Starts difference files testing.
 */
public class ITestFileContentEquals {

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
            AbstractTaskFactory taskFactory = new FileTaskFactory();
            AbstractStatisticFactory statisticFactory = new FileStatisticFactory();
            partName = specifiedFile.getAbsolutePath() + paramHolder.getValue("partSuffix") + "001";
            //creating processors
            splitProcessor = new FileProcessor(specifiedFile.getAbsolutePath(), partSize, paramHolder, iteratorFactory, taskFactory, statisticFactory);
            splitProcessor.process();
            mergeProcessor = new FileProcessor(partName, paramHolder, iteratorFactory, taskFactory, statisticFactory);
            mergeProcessor.process();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            e.printStackTrace();
        }
        assertEquals(true, equals);
    }

    /**
     * Deletes files created during testing.
     */
    @AfterClass
    public static void destroy() {
        specifiedFile.delete();
        List<File> files = Calculations.getPartsList(partName.substring(0, partName.indexOf(paramHolder.getValue("partSuffix"))), paramHolder.getValue("partSuffix"));
        for (File file : files) {
            file.delete();
        }
        resultFile.delete();
    }
}
