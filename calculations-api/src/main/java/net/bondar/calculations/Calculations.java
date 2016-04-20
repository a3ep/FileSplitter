package net.bondar.calculations;

import net.bondar.calculations.exceptions.CalculationsException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Contains methods for performing operations with files.
 */
public class Calculations {

    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger(Calculations.class);

    /**
     * Gets size of complete file.
     *
     * @param files list of part-files
     * @return complete file size
     */
    public static double getFileSize(List<File> files) {
        double fileSize = 0;
        for (File f : files) {
            fileSize += f.length();
        }
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
        if (finish - start > bufferSize) {
            return bufferSize;
        } else {
            return (int) (finish - start);
        }
    }

    /**
     * Gets the list of part-files.
     *
     * @param destination    path to processing file
     * @param partNameSuffix name suffix of the part-file
     * @return list of part-files
     * @throws CalculationsException when occurred exception during collecting part-files
     */
    public static List<File> getPartsList(String destination, String partNameSuffix) throws CalculationsException{
        File partFile = new File(destination);
        String destName = partFile.getName();
        File file = partFile.getParentFile();
        File[] files = file.listFiles((File dir, String name) -> name.matches(destName + partNameSuffix + ".+\\d+"));
        Arrays.sort(files);
        List<File> parts = new LinkedList<>();
        Collections.addAll(parts, files);
        if (parts.isEmpty()) {
            log.warn("Part-file not found. Please check your input");
            throw new CalculationsException("Error during collecting part-files. Exception: file not found. Please check your input");
        }
        return parts;
    }
}
