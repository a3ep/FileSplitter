package net.bondar.domain;


public enum Command {
    EXIT,
    SPLIT("", ""),
    MERGE("");

    /**
     *
     * @param firstParameter
     * @param secondParameter
     */
    Command(String firstParameter, String secondParameter) {
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
    public String getSecondParameter() {
        return secondParameter;
    }

    /**
     *
     * @param secondParameter
     */
    public void setSecondParameter(String secondParameter) {
        this.secondParameter = secondParameter;
    }

    /**
     *
     */
    private String firstParameter;

    /**
     *
     */
    private String secondParameter;
}
