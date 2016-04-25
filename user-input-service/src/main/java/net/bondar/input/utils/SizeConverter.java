package net.bondar.input.utils;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.IConverter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides converting specified size value.
 */
public class SizeConverter implements IConverter {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Byte value.
     */
    private static final long BYTE = 1;

    /**
     * Kilobyte value.
     */
    private static final long KILOBYTE = BYTE * 1024;

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
    private Map<String, Long> units = new HashMap<>();

    SizeConverter() {
        units.put("TB", TERABYTE);
        units.put("GB", GIGABYTE);
        units.put("MB", MEGABYTE);
        units.put("KB", KILOBYTE);
        units.put("B", BYTE);
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
    public String convert(String value) throws ParsingException {
        try {
            for (Map.Entry<String, Long> entry : units.entrySet()) {
                int unitIndex;
                if ((unitIndex = value.indexOf(entry.getKey())) != -1) {
                    log.debug("Current size unit: " + entry.getKey());
                    return String.valueOf(Integer.parseInt(value.substring(0, unitIndex)) * entry.getValue());
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
