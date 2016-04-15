package net.bondar.interfaces;

import java.util.Map;

/**
 *
 */
public interface IStatisticHolder {

    Map<String, Long> getStatistic();

    void putInformation(String key, Long value);
}
