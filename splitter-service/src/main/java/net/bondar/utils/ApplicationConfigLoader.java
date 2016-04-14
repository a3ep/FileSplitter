package net.bondar.utils;

import net.bondar.exceptions.ApplicationException;
import net.bondar.interfaces.IConfigLoader;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class ApplicationConfigLoader implements IConfigLoader {

    /**
     *
     */
    private final Logger log = Logger.getLogger("userLogger");

    /**
     *
     */
    private Properties properties;

    /**
     *
     */
    public ApplicationConfigLoader() {
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
     * @param value
     * @return
     */
    public String getValue(String value) {
        return properties.getProperty(value);
    }
}
