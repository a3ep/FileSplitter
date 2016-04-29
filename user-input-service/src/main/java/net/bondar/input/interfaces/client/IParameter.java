package net.bondar.input.interfaces.client;

/**
 * Interface for a enum contains parameters.
 */
public interface IParameter {

    /**
     * Gets parameter name.
     *
     * @return parameter name
     */
    String name();

    /**
     * Gets parameter identifier.
     *
     * @return parameter identifier
     */
    String getIdentifier();

    /**
     * Gets parsable flag.
     *
     * @return parsable flag
     */
    boolean isParsable();

    /**
     * Gets parameter value.
     *
     * @return current value
     */
    String getValue();

    /**
     * Sets parameter value.
     *
     * @param value setting value
     */
    void setValue(String value);

    /**
     * Gets parameter description.
     *
     * @return parameter description
     */
    String getDescription();
}
