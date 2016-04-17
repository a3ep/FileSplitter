package net.bondar.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Calculations {

    /**
     *
     * @param endValue
     * @param startValue
     * @param bufferSize
     * @return
     */
    public static int getAvaliableSize(long endValue, long startValue, int bufferSize){
        if (endValue - startValue > bufferSize) return bufferSize;
        else return (int) (endValue - startValue);
    }

    /**
     * @param dest
     * @return
     */
    public static List<File> getPartsList(String dest) {
        File partFile = new File(dest);
        String partName = partFile.getName();
        String destName = partName.substring(0, partName.indexOf("_part_"));
        File file = partFile.getParentFile();
        File[] files = file.listFiles((File dir, String name) -> name.matches(destName + ".+\\_\\d+"));
        Arrays.sort(files);
        List<File> parts = new LinkedList<>();
        Collections.addAll(parts, files);
        return parts;
    }
}
