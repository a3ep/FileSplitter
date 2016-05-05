package net.bondar.input.interfaces.client;

/**
 * Provides converting values.
 */
public interface IConverter {

    /**
     * Converts value.
     *
     * @param value value needs to convert
     * @return converted value
     */
    String convert(final String value);
}
