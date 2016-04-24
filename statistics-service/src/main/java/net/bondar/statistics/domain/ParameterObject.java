package net.bondar.statistics.domain;

import net.bondar.statistics.interfaces.IParameterObject;

/**
 * Provides holding statistical information parameters.
 */
public class ParameterObject implements IParameterObject {

    /**
     * Parameter name.
     */
    private final String name;

    /**
     * Parameter value.
     */
    private final String value;

    /**
     * Creates <code>ParameterObject</code> instance.
     *
     * @param name  parameter name
     * @param value parameter value
     */
    public ParameterObject(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Gets parameter name.
     *
     * @return parameter name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets parameter value.
     *
     * @return parameter value
     */
    public String getValue() {
        return value;
    }
}
