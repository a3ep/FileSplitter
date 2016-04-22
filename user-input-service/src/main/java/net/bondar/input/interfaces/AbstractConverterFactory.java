package net.bondar.input.interfaces;

import net.bondar.input.domain.Parameter;

/**
 * Interface for creating concrete converters.
 */
public interface AbstractConverterFactory {

    /**
     * Creates converter.
     *
     * @param parameter command parameter
     * @return concrete converter
     */
    IConverter createConverter(Parameter parameter);
}
