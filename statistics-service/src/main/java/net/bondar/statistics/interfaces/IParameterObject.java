package net.bondar.statistics.interfaces;

/**
 * Interface for class that provides holding statistical information parameters.
 */
public interface IParameterObject {

    /**
     * Gets parameter name.
     *
     * @return parameter name
     */
    String getName();

    /**
     * Gets parameter value.
     *
     * @return parameter value
     */
    String getValue();
}
