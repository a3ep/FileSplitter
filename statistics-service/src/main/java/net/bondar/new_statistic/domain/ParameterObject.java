package net.bondar.new_statistic.domain;

import net.bondar.new_statistic.interfaces.IParameterObject;

/**
 * Provides holding statistical information parameters.
 */
public class ParameterObject implements IParameterObject{

    /**
     * Parameter name.
     */
    private final String name;

    /**
     * Identifies needs to convert.
     */
    private final boolean convertible;

    /**
     * Parameter value.
     */
    private final String value;

    /**
     * Creates <code>ParameterObject</code> instance.
     *
     * @param name parameter name
     * @param convertible identifies needs to convert
     * @param value parameter value
     */
    public ParameterObject(String name, boolean convertible, String value) {
        this.name = name;
        this.convertible = convertible;
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
     * Gets convertible.
     *
     * @return convertible
     */
    public boolean isConvertible() {
        return convertible;
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
