package net.bondar;

import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.IProcessor;
import net.bondar.utils.MergeProcessor;
import net.bondar.utils.SplitMergeIteratorFactory;
import net.bondar.utils.SplitProcessor;
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
    private static List<File> splitParts;
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
            AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
            splitProcessor = new SplitProcessor(file.getAbsolutePath(), partSize, iteratorFactory);
            splitProcessor.process();
            mergeProcessor = new MergeProcessor(file.getAbsolutePath() + "_part_001", iteratorFactory);
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
        long splitPartsSize = 0;
        long mergePartsSize = 0;
        resultFile = mergeProcessor.getFile();
        splitParts = splitProcessor.getFiles();
        List<File> mergeParts = mergeProcessor.getFiles();
        for (int i = 0; i < splitParts.size(); i++) {
            splitPartsSize += splitParts.get(i).length();
            mergePartsSize += mergeParts.get(i).length();
        }
        assertEquals(splitProcessor.getFile().length(), resultFile.length());
        assertEquals(splitPartsSize, mergePartsSize);

    }

    /**
     *
     */
    @After
    public void destroy() {
        resultFile.delete();
        for (File f : splitParts) {
            f.delete();
        }
    }
}
