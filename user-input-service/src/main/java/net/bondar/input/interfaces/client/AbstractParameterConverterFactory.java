package net.bondar.input.interfaces.client;

/**
 * Interface for creating concrete converters.
 */
public interface AbstractParameterConverterFactory {

    /**
     * Creates converter.
     *
     * @param parameter command parameter
     * @return concrete converter
     */
    IConverter createConverter(IParameter parameter);
}
