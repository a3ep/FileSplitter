package net.bondar.core.interfaces;

/**
 * Interface holding application configurations and parameters.
 */
public interface IConfigHolder {

    /**
     * Gets the value of the specified parameter.
     *
     * @param key key of the specified parameter
     * @return parameter value
     */
    String getValue(String key);
}
