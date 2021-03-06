package net.bondar.splitter.utils;

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
    Parameter(String identifier, final boolean parsable, String value, String description) {
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
    public void setIdentifier(String identifier) {

    }

    @Override
    public boolean isParsable() {
        return parsable;
    }

    @Override
    public void setParsable(boolean parsable) {
        this.parsable = parsable;
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

    @Override
    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "Parameter{" +
                "identifier='" + identifier + '\'' +
                ", parsable=" + parsable +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
