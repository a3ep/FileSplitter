package net.bondar.input.utils;

import net.bondar.input.domain.Parameter;
import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.IParameterFinder;
import net.bondar.input.interfaces.IParameterHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides finding file parameters.
 */
public class FileParameterFinder implements IParameterFinder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Parameter holder.
     */
    private final IParameterHolder parameterHolder;

    /**
     * Creates <code>FileParameterFinder</code> instance.
     *
     * @param parameterHolder parameter holder
     */
    public FileParameterFinder(IParameterHolder parameterHolder) {
        this.parameterHolder = parameterHolder;
    }

    /**
     * Finds parameters in the specified array of strings.
     *
     * @param list specified array of strings
     * @return list of parameters
     * @throws ParsingException if parameters are not found
     * @see {@link IParameterHolder}
     */
    @Override
    public List<Parameter> findParameters(List<String> list) throws ParsingException {
        List<Parameter> result = new ArrayList<>();
        parameterHolder.getParameters().stream().filter(parameter -> list.contains(parameter.getIdentifier())).forEach(parameter -> {
            log.debug("Found parameter: " + parameter.name());
            int parameterIndex = list.indexOf(parameter.getIdentifier());
            if (parameterIndex < list.size() - 1) {
                parameter.setValue(list.get(parameterIndex + 1));
            }
            log.debug("Parameter " + parameter.name() + " added to list of parameters.");
            result.add(parameter);
        });
        if (!result.isEmpty()) {
            return result;
        }
        log.error("Error while finding parameters. Parameters not found.");
        throw new ParsingException("Error while finding parameters in " + list.toString() + ". Parameters not found.");
    }
}
