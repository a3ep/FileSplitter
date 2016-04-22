package net.bondar.input.utils;

import net.bondar.input.domain.Parameter;
import net.bondar.input.interfaces.AbstractConverterFactory;
import net.bondar.input.interfaces.IParameterParser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Provides parsing of parameter values.
 */
public class ParameterParser implements IParameterParser{

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Converter factory.
     */
    private final AbstractConverterFactory converterFactory;

    /**
     * Creates <code>ParameterParser</code> instance.
     *
     * @param converterFactory converter factory
     * @see {@link IParameterParser}
     */
    public ParameterParser(AbstractConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    /**
     * Parses parameter value.
     *
     * @param parameters list of parameters
     * @return list of parameters with correct parameter values
     * @see {@link IParameterParser}
     */
    @Override
    public List<Parameter> parseParameterValue(List<Parameter> parameters) {
        log.debug("Parsing parameter values.");
        parameters.stream().filter(Parameter::isParsable).forEach(parameter -> {
            log.debug("Found parsable parameter: " + parameter.name());
            parameter.setValue(converterFactory.createConverter(parameter).convert(parameter.getValue()));
        });
        return parameters;
    }
}
