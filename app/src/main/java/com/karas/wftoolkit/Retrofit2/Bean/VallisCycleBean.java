package com.karas.wftoolkit.Retrofit2.Bean;

/**
 * Created by Hongliang Luo on 2019/5/31.
 **/
public class VallisCycleBean {

    /**
     * id : vallisCycle1559265180000
     * expiry : 2019-05-31T01:33:48.000Z
     * isWarm : false
     * state : cold
     * activation : 2019-05-31T01:13:00.000Z
     * timeLeft : 1m 10s
     * shortString : 1m to Warm
     */

    private String id;
    private String expiry;
    private boolean isWarm;
    private String state;
    private String activation;
    private String timeLeft;
    private String shortString;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public boolean isIsWarm() {
        return isWarm;
    }

    public void setIsWarm(boolean isWarm) {
        this.isWarm = isWarm;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getShortString() {
        return shortString;
    }

    public void setShortString(String shortString) {
        this.shortString = shortString;
    }
}
