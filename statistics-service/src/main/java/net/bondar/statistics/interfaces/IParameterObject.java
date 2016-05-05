package net.bondar.statistics.interfaces;

import net.bondar.statistics.service.ParameterObject;

import java.util.List;

/**
 * Provides holding statistical information parameters.
 */
public interface IParameterObject {

    /**
     * Gets list of statistics records ids.
     *
     * @return list of statistics records ids
     */
    List<String> getListOfIds();

    /**
     * Gets current volume of performed work.
     *
     * @return current volume of performed work
     */
    long getCurrentVolume();

    /**
     * Gets total volume of work.
     *
     * @return total volume of work
     */
    double getTotalVolume();

    /**
     * Gets a list of <code>IParameterObject</code>s.
     *
     * @return a list of <code>IParameterObject</code>s
     */
    List<ParameterObject> getParameterList();
}
