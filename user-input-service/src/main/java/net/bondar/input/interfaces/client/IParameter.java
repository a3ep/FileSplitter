package net.bondar.input.interfaces.client;

/**
 * Contains parameters.
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
     * Sets parameter identifier.
     *
     * @param identifier setting identifier
     */
    void setIdentifier(String identifier);

    /**
     * Gets parsable flag.
     *
     * @return parsable flag
     */
    boolean isParsable();

    /**
     * Sets parsable value.
     *
     * @param value setting value
     */
    void setParsable(boolean value);

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

    /**
     * Sets paremeter description.
     *
     * @param description setting description
     */
    void setDescription(String description);
}
