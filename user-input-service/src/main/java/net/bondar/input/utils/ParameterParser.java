package net.bondar.input.utils;

import net.bondar.input.interfaces.client.AbstractParameterConverterFactory;
import net.bondar.input.interfaces.client.IParameter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Provides parsing of parameter values.
 */
public class ParameterParser {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Parameter finder.
     */
    private final ParameterFinder parameterFinder;

    /**
     * Converter factory.
     */
    private final AbstractParameterConverterFactory converterFactory;

    /**
     * Creates <code>ParameterParser</code> instance.
     *
     * @param parameterFinder  parameter finder
     * @param converterFactory converter factory
     */
    public ParameterParser(ParameterFinder parameterFinder, AbstractParameterConverterFactory converterFactory) {
        this.parameterFinder = parameterFinder;
        this.converterFactory = converterFactory;
    }

    /**
     * Parses parameter value.
     *
     * @param arguments list of arguments
     * @return list of parameters with correct parameter values
     */
    public List<IParameter> parse(final List<String> arguments) {
        List<IParameter> parameters = parameterFinder.find(arguments);
        log.debug("Start parsing parameters values...");
        parameters.stream().filter(IParameter::isParsable).forEach(parameter -> {
            log.debug("Found parsable parameter: " + parameter.name());
            parameter.setValue(converterFactory.createConverter(parameter).convert(parameter.getValue()));
        });
        log.debug("Finish parsing parameters values.");
        return parameters;
    }
}
