package net.bondar.input.domain;

/**
 * Contains user input parameters.
 */
public enum Parameter {

    /**
     * Path parameter.
     */
    PATH("-p", false, "", "Path to the file you want to split."),

    /**
     * Size parameter.
     */
    SIZE("-s", true, "", "Size of the parts (Example: 10/10KB/10MB/10GB/10TB).");

    /**
     * Creates <code>Parameter</code> instance.
     *
     * @param identifier parameter identifier
     * @param parsable   identifies needs to parse
     * @param value      parameter value
     */
    Parameter(String identifier, boolean parsable, String value, String description) {
        this.identifier = identifier;
        this.parsable = parsable;
        this.value = value;
        this.description = description;
    }

    /**
     * Gets parameter identifier.
     *
     * @return parameter identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Gets parsable flag.
     *
     * @return parsable flag
     */
    public boolean isParsable() {
        return parsable;
    }

    /**
     * Gets parameter value.
     *
     * @return current value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets parameter value.
     *
     * @param value setting value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets parameter description.
     *
     * @return parameter description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Parameter identifier.
     */
    private String identifier;

    /**
     * Identifies needs to parse.
     */
    private boolean parsable;

    /**
     * Parameter value.
     */
    private String value;

    /**
     * Parameter description.
     */
    private String description;

}
