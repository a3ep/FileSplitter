package net.bondar.new_statistic.interfaces;

/**
 * Interface for class that provides holding statistical information parameters.
 */
public interface IParameterObject {

    /**
     * Gets parameter name.
     *
     * @return parameter name
     */
    public String getName();

    /**
     * Gets convertible.
     *
     * @return convertible
     */
    public boolean isConvertible();

    /**
     * Gets parameter value.
     *
     * @return parameter value
     */
    public String getValue();
}
