package net.bondar.input.interfaces;

import net.bondar.input.interfaces.client.IParameter;

import java.util.List;

/**
 * Interface for class that provides finding parameters.
 */
public interface IParameterFinder {

    /**
     * Finds parameters.
     *
     * @param arguments list of arguments
     * @return list of current parameters
     */
    List<IParameter> find(List<String> arguments);

}
