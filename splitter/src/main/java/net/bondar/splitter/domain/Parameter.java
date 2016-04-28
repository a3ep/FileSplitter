package net.bondar.splitter.domain;

import net.bondar.input.interfaces.client.IParameter;

/**
 * Contains user input parameters.
 */
public enum Parameter implements IParameter {

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

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public boolean isParsable() {
        return parsable;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
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
