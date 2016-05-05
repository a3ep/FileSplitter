package net.bondar.input.interfaces.client;

/**
 * Provides creating concrete converters.
 */
public interface AbstractParameterConverterFactory {

    /**
     * Creates converter.
     *
     * @param parameter command parameter
     * @return concrete converter
     */
    IConverter createConverter(final IParameter parameter);
}
