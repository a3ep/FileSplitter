package net.bondar.core;

import net.bondar.statistics.interfaces.client.IStatObject;
import net.bondar.statistics.interfaces.client.IStatisticsParameter;

import java.util.Map;
import java.util.TreeMap;

/**
 * Contains parameters for calculating statistical information.
 */
public class FileStatObject implements IStatObject {

    /**
     * Map of parameters.
     */
    private final Map<IStatisticsParameter, Long> parameters = new TreeMap<>();

    /**
     * Creates <code>FileStatObject</code> instance.
     *
     * @param startPosition    start part-file position index
     * @param endPosition      end part-file position index
     * @param partWrittenSize  the size of bytes written to the part of file
     * @param totalWrittenSize the total size of bytes written to the file
     * @param fileSize         the size of file
     */
    public FileStatObject(final long startPosition, final long endPosition, final long partWrittenSize, final long totalWrittenSize, final long fileSize) {
        parameters.put(FileStatisticsParameter.PART_WRITTEN, partWrittenSize);
        parameters.put(FileStatisticsParameter.PART_SIZE, endPosition - startPosition);
        parameters.put(FileStatisticsParameter.TOTAL_WRITTEN, totalWrittenSize);
        parameters.put(FileStatisticsParameter.TOTAL_SIZE, fileSize);
    }

    @Override
    public Map<IStatisticsParameter, Long> getAllParameters() {
        return parameters;
    }

    @Override
    public long getParameterValue(IStatisticsParameter parameter) {
        return parameters.get(parameter);
    }

    @Override
    public String toString() {
        return "FileStatObject{" +
                "parameters=" + parameters +
                '}';
    }
}
