package net.bondar.input.utils;


import net.bondar.input.interfaces.client.IParameter;

import java.util.List;

/**
 * Provides holding parameters.
 */
public class ParameterHolder {

    /**
     * List of parameters.
     */
    private final List<IParameter> parameters;

    /**
     * Creates <code>ParameterHolder</code> instance.
     *
     * @param parameters list of parameters
     */
    public ParameterHolder(List<IParameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Gets list of parameters.
     *
     * @return list of parameters
     */
    public List<IParameter> getParameters() {
        return parameters;
    }
}
