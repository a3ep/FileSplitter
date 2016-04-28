package net.bondar.input.interfaces;

import net.bondar.input.interfaces.client.IParameter;

import java.util.List;

/**
 * Interface for a class that provides parsing of a parameter value.
 */
public interface IParameterParser {

    /**
     * Parses parameter value.
     *
     * @param arguments list of arguments
     * @return list of parameters with correct parameter values
     */
    List<IParameter> parse(List<String> arguments);
}
