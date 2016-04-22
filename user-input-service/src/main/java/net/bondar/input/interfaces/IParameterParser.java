package net.bondar.input.interfaces;

import net.bondar.input.domain.Parameter;

import java.util.List;

/**
 * Interface for a class that provides parsing of a parameter value.
 */
public interface IParameterParser {

    /**
     * Parses parameter value.
     *
     * @param parameters list of parameters
     * @return list of parameters with correct parameter values
     */
    List<Parameter> parseParameterValue(List<Parameter> parameters);
}
