package net.bondar.core.utils;

import net.bondar.core.exceptions.RunException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides finding files.
 */
public class FilesFinder {

    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger(FilesFinder.class);

    /**
     * Gets the list of part-files.
     *
     * @param destination    path to processing file
     * @param partNameSuffix name suffix of the part-file
     * @return list of part-files
     * @throws RunException when occurred exception during collecting part-files
     */
    public static List<File> getPartsList(final String destination, final String partNameSuffix) throws RunException {
        File specifiedFile = new File(destination);
        String destName = specifiedFile.getName();
        File file = specifiedFile.getParentFile();
        File[] files = file.listFiles((File dir, String name) -> name.matches(destName + partNameSuffix + ".+\\d+"));
        Arrays.sort(files);
        List<File> parts = new LinkedList<>();
        Collections.addAll(parts, files);
        if (parts.isEmpty()) {
            log.error("Part-file not found. Please check your input");
            throw new RunException("Error during collecting part-files. Exception: file not found. Please check your input");
        }
        return parts;
    }
}
