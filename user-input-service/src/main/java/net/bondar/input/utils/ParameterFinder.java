package net.bondar.input.utils;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.client.IParameter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides finding parameters.
 */
public class ParameterFinder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Parameter holder.
     */
    private final ParameterHolder parameterHolder;

    /**
     * Creates <code>ParameterFinder</code> instance.
     *
     * @param parameterHolder parameter holder
     */
    public ParameterFinder(ParameterHolder parameterHolder) {
        this.parameterHolder = parameterHolder;
    }

    /**
     * Finds parameters in the specified array of strings.
     *
     * @param arguments list of arguments
     * @return list of parameters
     * @throws ParsingException if parameters are not found
     */
    public List<IParameter> find(final List<String> arguments) throws ParsingException {
        List<IParameter> result = new ArrayList<>();
        log.debug("Start finding command parameters...");
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
            log.debug("Finish finding command parameters. Parameters: " + result.toString());
            return result;
        }
        log.error("Error while finding parameters. Parameters not found.");
        throw new ParsingException("Error while finding parameters in " + arguments.toString() + ". Parameters not found.");
    }
}
