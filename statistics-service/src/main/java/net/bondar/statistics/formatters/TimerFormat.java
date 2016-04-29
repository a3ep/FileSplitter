package net.bondar.statistics.formatters;

/**
 * Contains timer formats.
 */
public enum TimerFormat {

    SECONDS {
        @Override
        public String format(double value) {
            return String.format("%.1f", value) + " c";
        }
    },
    MINUTES {
        @Override
        public String format(double value) {
            return String.format("%.1f", value / 60) + " m";
        }
    };

    /**
     * Formats value.
     *
     * @param value current value
     * @return string with formatted value
     */
    public abstract String format(double value);
}
