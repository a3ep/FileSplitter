package net.bondar.input.utils;

import net.bondar.input.domain.Parameter;
import net.bondar.input.interfaces.AbstractConverterFactory;
import net.bondar.input.interfaces.IConverter;

/**
 * Provides creating parameter converter.
 */
public class ConverterFactory implements AbstractConverterFactory {

    /**
     * Creates concrete parameter converter.
     *
     * @param parameter command parameter
     * @return concrete parameter converter
     * @see {@link AbstractConverterFactory}
     */
    @Override
    public IConverter createConverter(Parameter parameter) {
        return createSizeConverter();
    }

    /**
     * Creates <code>SizeConverter</code>.
     *
     * @return <code>SizeConverter</code> instance
     * @see {@link AbstractConverterFactory}
     */
    private IConverter createSizeConverter() {
        return new SizeConverter();
    }
}
