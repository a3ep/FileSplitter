package net.bondar.input.interfaces;

/**
 * Interface for class that converting values.
 */
public interface IConverter {

    /**
     * Converts value.
     *
     * @param value value needs to convert
     * @return converted value
     */
    String convert(String value);
}
