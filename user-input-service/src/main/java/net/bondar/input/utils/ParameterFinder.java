package net.bondar.input.utils;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.IParameterFinder;
import net.bondar.input.interfaces.IParameterHolder;
import net.bondar.input.interfaces.client.IParameter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides finding parameters.
 */
public class ParameterFinder implements IParameterFinder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Parameter holder.
     */
    private final IParameterHolder parameterHolder;

    /**
     * Creates <code>ParameterFinder</code> instance.
     *
     * @param parameterHolder parameter holder
     */
    public ParameterFinder(IParameterHolder parameterHolder) {
        this.parameterHolder = parameterHolder;
    }

    /**
     * Finds parameters in the specified array of strings.
     *
     * @param arguments list of arguments
     * @return list of parameters
     * @throws ParsingException if parameters are not found
     * @see {@link IParameterHolder}
     */
    @Override
    public List<IParameter> find(final List<String> arguments) throws ParsingException {
        List<IParameter> result = new ArrayList<>();
        parameterHolder.getParameters().stream().filter(parameter -> arguments.contains(parameter.getIdentifier())).forEach(parameter -> {
            log.debug("Found parameter: " + parameter.name());
            int parameterIndex = arguments.indexOf(parameter.getIdentifier());
            if (parameterIndex < arguments.size() - 1) {
                parameter.setValue(arguments.get(parameterIndex + 1));
            }
            log.debug("Parameter " + parameter.name() + " added to list of parameters.");
            result.add(parameter);
        });
        if (!result.isEmpty()) {
            return result;
        }
        log.error("Error while finding parameters. Parameters not found.");
        throw new ParsingException("Error while finding parameters in " + arguments.toString() + ". Parameters not found.");
    }
}
