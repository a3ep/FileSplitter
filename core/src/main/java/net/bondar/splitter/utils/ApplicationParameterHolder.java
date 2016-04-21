package net.bondar.splitter.utils;

import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.IParameterHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Holds application configurations and parameters.
 */
public class ApplicationParameterHolder implements IParameterHolder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Application properties.
     */
    private Properties properties;

    /**
     * Creates <code>ApplicationParameterHolder</code> instance.
     *
     * @throws ApplicationException when occurred exception during loading properties
     * @see {@link IParameterHolder}
     */
    public ApplicationParameterHolder() throws ApplicationException {
        properties = new Properties();
        String propertiesFile = "config.properties";
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                log.warn("Property file " + propertiesFile + " not found in the classpath)");
                throw new ApplicationException("Property file " + propertiesFile + " not found in the classpath)");
            }
        } catch (IOException e) {
            log.warn("Catches IOException, during loading properties. Message " + e.getMessage());
            throw new ApplicationException("Error during loading properties. Exception:" + e.getMessage());
        }
    }

    /**
     * Gets the value of the specified parameter.
     *
     * @param key key of the specified parameter
     * @return parameter value
     * @see {@link IParameterHolder}
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
