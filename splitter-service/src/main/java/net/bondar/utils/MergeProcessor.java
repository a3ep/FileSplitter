package net.bondar.utils;

import net.bondar.interfaces.AbstractIteratorFactory;
import net.bondar.interfaces.IProcessor;
import net.bondar.interfaces.Iterable;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class MergeProcessor implements IProcessor {

    /**
     *
     */
    private String partFileDest;

    /**
     *
     */
    private AbstractIteratorFactory iFactory;

    /**
     * @param partFileDest
     * @param iFactory
     */
    public MergeProcessor(String partFileDest, AbstractIteratorFactory iFactory) {
        this.partFileDest = partFileDest;
        this.iFactory = iFactory;
    }

    /**
     *
     */
    @Override
    public void process() {
        List<File> partFileList = getPartsList(partFileDest);
        File completeFile = new File(partFileList.get(0).getParent(), partFileList.get(0).getName().substring(0, partFileList.get(0).getName().indexOf("_")));
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(completeFile))) {
            for (File file : partFileList) {
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                    Iterable iterator = iFactory.createIterator(bis, null);
                    while (iterator.hasNext()) {
                        bos.write(iterator.next());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<File> getPartsList(String dest) {
        File partFile = new File(dest);
        String partName = partFile.getName();
        String destName = partName.substring(0, partName.indexOf("_part_"));
        File file = partFile.getParentFile();
        File[] files = file.listFiles((File dir, String name) -> name.matches(destName + ".+\\_\\d+"));
        Arrays.sort(files);
        return Arrays.asList(files);
    }
}
