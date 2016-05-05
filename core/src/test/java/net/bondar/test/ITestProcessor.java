package net.bondar.test;

import net.bondar.calculations.FileCalculationUtils;
import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.interfaces.factories.AbstractCloseTaskFactory;
import net.bondar.core.interfaces.factories.AbstractIteratorFactory;
import net.bondar.core.interfaces.factories.AbstractTaskFactory;
import net.bondar.core.utils.ConfigHolder;
import net.bondar.core.utils.FileProcessor;
import net.bondar.core.utils.factories.CloseTaskFactory;
import net.bondar.core.utils.factories.IteratorFactory;
import net.bondar.core.utils.factories.TaskFactory;
import net.bondar.statistics.interfaces.IStatisticsService;
import net.bondar.statistics.service.StatisticsService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.easymock.EasyMock.*;

/**
 * Test file processor.
 */
public class ITestProcessor {

    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger(ITestProcessor.class);

    /**
     * Split command name.
     */
    private static final String SPLIT_COMMAND = "split";

    /**
     * Merge command name.
     */
    private static final String MERGE_COMMAND = "merge";

    /**
     * Size of the part-file;
     */
    private static final int PART_SIZE = 1024 * 1024;

    /**
     * Part-file suffix.
     */
    private static String partSuffix;

    /**
     * Parameter holder.
     */
    private static IConfigHolder configHolder;

    /**
     * Iterator factory.
     */
    private static AbstractIteratorFactory iteratorFactory;

    /**
     * Task factory.
     */
    private static AbstractTaskFactory taskFactory;

    /**
     * Closing task factory.
     */
    private static AbstractCloseTaskFactory closeTaskFactory;

    /**
     * Statistic service.
     */
    private static IStatisticsService statisticsService;

    /**
     * File specified by user.
     */
    private static File specifiedFile;

    /**
     * Part-files specified by user.
     */
    private static List<File> specifiedParts = new ArrayList<>();

    /**
     * Runs preparation for testing.
     */
    @BeforeTest
    public static void setUp() {
        // initializing processor's components
        configHolder = EasyMock.createMock(ConfigHolder.class);
        expect(configHolder.getValue("partSuffix")).andReturn("_part_").anyTimes();
        expect(configHolder.getValue("threadsCount")).andReturn("3").anyTimes();
        expect(configHolder.getValue("bufferSize")).andReturn("1048576").anyTimes();
        expect(configHolder.getValue("statisticsTimer")).andReturn("1000").anyTimes();
        EasyMock.replay(configHolder);
        partSuffix = configHolder.getValue("partSuffix");
        iteratorFactory = new IteratorFactory();
        taskFactory = new TaskFactory();
        closeTaskFactory = new CloseTaskFactory();
        statisticsService = EasyMock.createMock(StatisticsService.class);
        statisticsService.showStatInfo(anyInt());
        expectLastCall().anyTimes();
        statisticsService.stop();
        expectLastCall().anyTimes();
        statisticsService.holdInformation(anyString(), anyObject());
        expectLastCall().anyTimes();
        EasyMock.replay(statisticsService);
    }

    /**
     * Creates testing files.
     */
    @BeforeClass
    public void createTestingData() {
        specifiedFile = createFile(System.getProperty("java.io.tmpdir") + "/test.txt", 3);
        new File(System.getProperty("java.io.tmpdir") + "/pattern").mkdir();
        specifiedParts = new ArrayList<>(createParts(System.getProperty("java.io.tmpdir") +
                "/pattern/test.txt" + partSuffix + "00", specifiedFile.length()));
    }

    /**
     * Tests splitting.
     */
    @Test
    public void testProcessSplit() {
        try {
            new FileProcessor(specifiedFile.getAbsolutePath(), PART_SIZE, configHolder, iteratorFactory,
                    taskFactory, closeTaskFactory, statisticsService, SPLIT_COMMAND).process();
            List<File> resultParts = FileCalculationUtils.getPartsList(specifiedFile.getAbsolutePath(), partSuffix);
            assertEquals(specifiedParts.size(), resultParts.size());
            for (int i = 0; i < specifiedParts.size(); i++) {
                assertTrue(FileUtils.contentEquals(specifiedParts.get(i), resultParts.get(i)));
            }
        } catch (IOException e) {
            log.error("Error while checking parts content equals. Message: " + e.getMessage());
        }
    }

    /**
     * Tests merging.
     */
    @Test
    public void testProcessMerge() {
        try {
            FileProcessor mergeProcessor = new FileProcessor(specifiedParts.get(0).getAbsolutePath(),
                    configHolder, iteratorFactory, taskFactory, closeTaskFactory, statisticsService, MERGE_COMMAND);
            mergeProcessor.process();
            File resultFile = mergeProcessor.getFile();
            assertEquals(specifiedFile.length(), resultFile.length());
            assertTrue(FileUtils.contentEquals(specifiedFile, resultFile));
        } catch (IOException e) {
            log.error("Error while checking parts content equals. Message: " + e.getMessage());
        }
    }

    /**
     * Deletes temporary files.
     */
    @AfterClass
    public static void destroy() {
        for (File file : specifiedParts) {
            assertTrue(file.delete());
        }
        assertTrue(specifiedFile.delete());
    }

    /**
     * Creates file for testing.
     *
     * @param dest file destination
     * @param size file size
     * @return complete file
     */
    private static File createFile(final String dest, final int size) {
        File file = new File(dest);
        try (RandomAccessFile output = new RandomAccessFile(file, "rw")) {
            byte[] buffer = new byte[PART_SIZE];
            while (file.length() < size * PART_SIZE) {
                output.write(buffer);
            }
        } catch (IOException e) {
            log.error("Error while creating testing file. Message: " + e.getMessage());
        }
        return file;
    }

    /**
     * Creates part-files for testing.
     *
     * @param dest part-file destination
     * @param size complete file size
     * @return list of part-files
     */
    private static List<File> createParts(final String dest, final long size) {
        List<File> parts = new ArrayList<>();
        long tmp = 0;
        for (int i = 1; tmp < size; i++) {
            File part = new File(dest + i);
            // calculating part-file size
            byte[] buffer = new byte[(size - tmp) == PART_SIZE ? (int) (PART_SIZE - (size / PART_SIZE - 1)) : PART_SIZE];
            try (RandomAccessFile output = new RandomAccessFile(part, "rw")) {
                output.write(buffer);
            } catch (IOException e) {
                log.error("Error while creating testing part-files. Message: " + e.getMessage());
            }
            tmp += PART_SIZE;
            parts.add(part);
        }
        return parts;
    }
}
