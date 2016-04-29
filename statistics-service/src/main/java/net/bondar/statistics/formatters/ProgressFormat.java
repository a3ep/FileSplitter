package net.bondar.statistics.formatters;

/**
 * Contains progress formats.
 */
public enum ProgressFormat {

    PERCENTAGE {
        @Override
        public String format(double value) {
            return (int) (value * 100) + "%";
        }
    },
    FRACTIONAL {
        @Override
        public String format(double value) {
            return String.format("%.2f", value);
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
