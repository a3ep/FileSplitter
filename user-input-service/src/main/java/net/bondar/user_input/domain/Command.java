package net.bondar.user_input.domain;


public enum Command {
    EXIT,
    SPLIT("", 0),
    MERGE("");

    /**
     *
     * @param firstParameter
     * @param secondParameter
     */
    Command(String firstParameter, long secondParameter) {
        this.firstParameter = firstParameter;
        this.secondParameter = secondParameter;
    }

    /**
     *
     * @param firstParameter
     */
    Command(String firstParameter) {
        this.firstParameter = firstParameter;
    }

    /**
     *
     */
    Command() {
    }

    /**
     *
     * @return
     */
    public String getFirstParameter() {
        return firstParameter;
    }

    /**
     *
     * @param firstParameter
     */
    public void setFirstParameter(String firstParameter) {
        this.firstParameter = firstParameter;
    }

    /**
     *
     * @return
     */
    public long getSecondParameter() {
        return secondParameter;
    }

    /**
     *
     * @param secondParameter
     */
    public void setSecondParameter(long secondParameter) {
        this.secondParameter = secondParameter;
    }

    /**
     *
     */
    private String firstParameter;

    /**
     *
     */
    private long secondParameter;
}
