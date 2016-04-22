package net.bondar.input.interfaces;

import net.bondar.input.domain.Parameter;

import java.util.List;

/**
 * Interface for class that provides finding parameters.
 */
public interface IParameterFinder {

    /**
     * Finds parameters.
     *
     * @param list list of arguments
     * @return list of current parameters
     */
    List<Parameter> findParameters(List<String> list);
}
