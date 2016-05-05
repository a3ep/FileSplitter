package net.bondar.core.utils;

import net.bondar.core.exceptions.RunException;
import net.bondar.core.interfaces.IConfigHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Holds application configurations and parameters.
 */
public class ConfigHolder implements IConfigHolder {

    /**
     * Application properties.
     */
    private Properties properties;

    /**
     * Creates <code>ConfigHolder</code> instance.
     *
     * @throws RunException when occurred exception during loading properties
     * @see {@link IConfigHolder}
     */
    public ConfigHolder() throws RunException {
        properties = new Properties();
        String propertiesFile = "config.properties";
        Logger log = LogManager.getLogger(getClass());
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                log.error("Property file " + propertiesFile + " not found in the classpath)");
                throw new RunException("Property file " + propertiesFile + " not found in the classpath)");
            }
        } catch (IOException e) {
            log.warn("Error during loading properties. Message " + e.getMessage());
            throw new RunException("Error during loading properties. Exception:" + e.getMessage());
        }
    }

    /**
     * Gets the value of the specified parameter.
     *
     * @param key key of the specified parameter
     * @return parameter value
     * @see {@link IConfigHolder}
     */
    public String getValue(final String key) {
        return properties.getProperty(key);
    }
}
