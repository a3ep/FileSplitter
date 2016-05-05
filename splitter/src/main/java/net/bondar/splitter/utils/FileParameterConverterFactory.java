package net.bondar.splitter.utils;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.client.AbstractParameterConverterFactory;
import net.bondar.input.interfaces.client.IConverter;
import net.bondar.input.interfaces.client.IParameter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides creating parameter converter.
 */
public class FileParameterConverterFactory implements AbstractParameterConverterFactory {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Creates concrete parameter converter.
     *
     * @param parameter command parameter
     * @return concrete parameter converter
     * @throws ParsingException if converter not found
     * @see {@link AbstractParameterConverterFactory}
     */
    @Override
    public IConverter createConverter(final IParameter parameter) throws ParsingException{
        if(parameter.equals(Parameter.SIZE)){
            return new SizeConverter();
        }
        log.error("Error while creating converter for parameter "+parameter.name());
        throw new ParsingException("Error while creating converter. Unable to find converter for parameter "+parameter.name());
    }
}
