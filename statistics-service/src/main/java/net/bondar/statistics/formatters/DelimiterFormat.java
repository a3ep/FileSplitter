package net.bondar.statistics.formatters;

/**
 * Contains delimiter formats.
 */
public enum DelimiterFormat {

    DOT("."),
    COMMA(","),
    COLON(":"),
    SEMICOLON(";");

    /**
     * Creates <code>DelimiterFormat</code> instance.
     *
     * @param value delimiter value
     */
    DelimiterFormat(String value) {
        this.value = value;
    }

    /**
     * Delimiter value.
     */
    private final String value;

    /**
     * Gets delimiter value.
     *
     * @return delimiter value
     */
    public String getValue() {
        return value;
    }
}
