package net.bondar.domain;


public enum Command {
    EXIT,
    SPLIT("", 0),
    MERGE("");

    /**
     *
     * @param firstParameter
     * @param secondParameter
     */
    Command(String firstParameter, int secondParameter) {
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
    public int getSecondParameter() {
        return secondParameter;
    }

    /**
     *
     * @param secondParameter
     */
    public void setSecondParameter(int secondParameter) {
        this.secondParameter = secondParameter;
    }

    /**
     *
     */
    private String firstParameter;

    /**
     *
     */
    private int secondParameter;
}
