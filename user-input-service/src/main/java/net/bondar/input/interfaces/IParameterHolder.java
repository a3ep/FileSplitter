package net.bondar.input.interfaces;

import net.bondar.input.interfaces.client.IParameter;

import java.util.List;

/**
 * Provides holding parameters.
 */
public interface IParameterHolder {

    /**
     * Gets list of parameters.
     *
     * @return list of parameters
     */
    List<IParameter> getParameters();
}
