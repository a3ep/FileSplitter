package net.bondar.test.utils;

import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.client.AbstractParameterConverterFactory;
import net.bondar.input.interfaces.client.IConverter;
import net.bondar.input.interfaces.client.IParameter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Provides creating parameter converter.
 */
public class TestParameterConverterFactory implements AbstractParameterConverterFactory {

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
    public IConverter createConverter(IParameter parameter) throws ParsingException{
        if(parameter.equals(TestParameter.SIZE)){
            return new TestConverter();
        }
        log.error("Error while creating converter for parameter "+parameter.name());
        throw new ParsingException("Error while creating converter. Unable to find converter for parameter "+parameter.name());
    }
}
