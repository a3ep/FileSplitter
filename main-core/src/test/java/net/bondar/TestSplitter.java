package net.bondar;

import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.IProcessor;
import net.bondar.utils.MergeProcessor;
import net.bondar.utils.SplitMergeIteratorFactory;
import net.bondar.utils.SplitProcessor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


/**
 *
 */
public class TestSplitter {
    private static IProcessor splitProcessor;
    private static IProcessor mergeProcessor;
    private static String fileDest = "/home/vsevolod/IdeaProjects/FileSplitter/main-core/src/test/resources/split/Test.ogg";
    private static String partDest = "/home/vsevolod/IdeaProjects/FileSplitter/main-core/src/test/resources/merge/Test.ogg_part_001";
    private static List<File> splitParts;
    private static File resultFile;

    @BeforeClass
    public static void setUp() {
        File file = new File(fileDest);
        File part = new File(partDest);
        int partSize = 1024 * 1024;
        AbstractIteratorFactory iteratorFactory = new SplitMergeIteratorFactory();
        splitProcessor = new SplitProcessor(file.getAbsolutePath(), partSize, iteratorFactory);
        mergeProcessor = new MergeProcessor(part.getAbsolutePath(), iteratorFactory);
    }

    @Test
    public void testFileSplitter() {
        long splitPartsSize = 0;
        long mergePartsSize = 0;
        splitProcessor.process();
        mergeProcessor.process();
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

    @AfterClass
    public static void destroy() {
        resultFile.delete();
        for (File f: splitParts){
            f.delete();
        }
    }
}
