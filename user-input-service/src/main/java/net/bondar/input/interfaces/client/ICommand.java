package net.bondar.input.interfaces.client;

import java.util.List;

/**
 * Contains commands.
 */
public interface ICommand {

    /**
     * Gets command name.
     *
     * @return command name
     */
    String name();

    /**
     * Gets parametric flag.
     *
     * @return parametric flag
     */
    boolean isParametric();

    /**
     * Sets parametric value.
     *
     * @param value setting value
     */
    void setParametric(boolean value);

    /**
     * Gets parameters.
     *
     * @return list of parameters
     */
    List<IParameter> getParameters();

    /**
     * Sets parameters.
     *
     * @param parameters list of parameters
     */
    void setParameters(List<IParameter> parameters);

    /**
     * Gets parameters values.
     *
     * @return list of parameters values
     */
    List<String> getParametersValues();

    /**
     * Gets command description.
     *
     * @return command description
     */
    String getDescription();

    /**
     * Sets command description.
     *
     * @param description setting description
     */
    void setDescription(String description);
}
