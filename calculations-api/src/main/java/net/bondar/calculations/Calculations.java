package net.bondar.calculations;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Conatins methods for performing coperations with files.
 */
public class Calculations {

    /**
     * Logger.
     */
    private static final Logger log = Logger.getLogger(Calculations.class);

    /**
     * Gets size of complete file.
     *
     * @param files list of part-files
     * @return complete file size
     */
    public static double getFileSize(List<File> files) {
        log.info("Calculating file size...");
        double fileSize = 0;
        for (File f : files) {
            fileSize += f.length();
        }
        log.info("File size = " + fileSize);
        return fileSize;
    }

    /**
     * Gets the required size of the byte array to write into the file.
     *
     * @param finish     index of the end position in a file.
     * @param start      index of the start position in a file.
     * @param bufferSize default buffer size
     * @return required byte array size
     */
    public static int getAvailableSize(long finish, long start, int bufferSize) {
        log.info("Getting available bytes array size");
        if (finish - start > bufferSize) {
            log.info("Available bytes array size = " + bufferSize);
            return bufferSize;
        } else {
            log.info("Available bytes array size = " + (int) (finish - start));
            return (int) (finish - start);
        }
    }

    /**
     * Gets the list of part-files.
     *
     * @param partFileDest   destination of the part-file
     * @param partNameSuffix name suffix of thw part-file
     * @return list of part-files
     */
    public static List<File> getPartsList(String partFileDest, String partNameSuffix) {
        log.info("Getting list of part-files");
        File partFile = new File(partFileDest);
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
