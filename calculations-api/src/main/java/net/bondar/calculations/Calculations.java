package net.bondar.calculations;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 */
public class Calculations {

    /**
     * Logger.
     */
    private static final Logger log = Logger.getLogger(Calculations.class);

    /**
     *
     * @param files
     * @return
     */
    public static double getFileSize(List<File> files){
        log.info("Calculating file size...");
        double fileSize=0;
        for(File f: files){
            fileSize+=f.length();
        }
        log.info("File size = "+fileSize);
        return fileSize;
    }

    /**
     *
     * @param finish
     * @param start
     * @param bufferSize
     * @return
     */
    public static int getAvailableSize(long finish, long start, int bufferSize){
        log.info("Getting available bytes array size");
        if(finish-start>bufferSize) {
            log.info("Available bytes array size = " + bufferSize);
            return bufferSize;
        } else {
            log.info("Available bytes array size = " + (int)(finish-start));
            return (int) (finish-start);
        }
    }

    /**
     *
     * @param fileDest
     * @param partNameSuffix
     * @return
     */
    public static List<File> getPartsList(String fileDest, String partNameSuffix){
        log.info("Getting list of part-files");
        File partFile = new File(fileDest);
        String partName = partFile.getName();
        String destName = partName.substring(0, partName.indexOf(partNameSuffix));
        File file = partFile.getParentFile();
        File[] files = file.listFiles((File dir, String name) -> name.matches(destName + ".+\\d+"));
        Arrays.sort(files);
        List<File> parts = new LinkedList<>();
        Collections.addAll(parts, files);
        log.info("Part-files list -> " + parts.toString());
        return parts;
    }
}
