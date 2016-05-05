package net.bondar.splitter.utils;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.client.IConverter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Provides converting specified size value.
 */
public class SizeConverter implements IConverter {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Kilobyte value.
     */
    private static final long KILOBYTE = 1024;

    /**
     * Megabyte value.
     */
    private static final long MEGABYTE = KILOBYTE * 1024;

    /**
     * Gigabyte value.
     */
    private static final long GIGABYTE = MEGABYTE * 1024;

    /**
     * Terabyte value.
     */
    private static final long TERABYTE = GIGABYTE * 1024;

    /**
     * Map with size units.
     */
    private Map<String, Long> units = new TreeMap<>();

    /**
     * Creates <code>SizeConverter</code> instance.
     */
    SizeConverter() {
        units.put("TB", TERABYTE);
        units.put("GB", GIGABYTE);
        units.put("MB", MEGABYTE);
        units.put("KB", KILOBYTE);
    }

    /**
     * Converts the specified size value to bytes.
     *
     * @param value value needs to convert
     * @return size in bytes
     * @throws ParsingException if receiver incorrect value
     * @see {@link IConverter}
     */
    @Override
    public String convert(final String value) throws ParsingException {
        log.debug("Start converting parameter value: " + value);
        try {
            for (Map.Entry<String, Long> entry : units.entrySet()) {
                int unitIndex;
                if ((unitIndex = value.indexOf(entry.getKey())) != -1) {
                    log.debug("Current size unit: " + entry.getKey());
                    String convertedValue = String.valueOf(Integer.parseInt(value.substring(0, unitIndex)) * entry.getValue());
                    log.debug("Finish converting parameter value. Converted value: " + convertedValue + " bytes.");
                    return convertedValue;
                }
            }
        } catch (NumberFormatException e) {
            log.error("Error while converting size value: " + value);
            throw new ParsingException("Error converting size value: " + value);
        }
        log.error("Error while converting size value: " + value);
        throw new ParsingException("Error converting size value: " + value);
    }
}
