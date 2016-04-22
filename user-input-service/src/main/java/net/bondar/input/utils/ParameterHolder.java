package net.bondar.input.utils;


import net.bondar.input.domain.Parameter;
import net.bondar.input.interfaces.IParameterHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Provides holding parameters.
 */
public class ParameterHolder implements IParameterHolder {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * List of parameters.
     */
    private final List<Parameter> parameters = Arrays.asList(Parameter.values());

    /**
     * Gets list of parameters.
     *
     * @return list of parameters
     * @see {@link IParameterHolder}
     */
    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
