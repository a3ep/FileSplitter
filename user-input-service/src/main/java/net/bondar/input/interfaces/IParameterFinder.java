package net.bondar.input.interfaces;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.client.IParameter;

import java.util.List;

/**
 * Provides finding parameters.
 */
public interface IParameterFinder {

    /**
     * Finds parameters.
     *
     * @param arguments list of arguments
     * @return list of current parameters
     * @throws ParsingException if parameters are not found
     */
    List<IParameter> find(final List<String> arguments) throws ParsingException;
}
