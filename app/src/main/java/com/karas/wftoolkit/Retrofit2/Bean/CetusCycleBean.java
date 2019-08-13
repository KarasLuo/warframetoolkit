package com.karas.wftoolkit.Retrofit2.Bean;

/**
 * Created by Hongliang Luo on 2019/5/30.
 **/
public class CetusCycleBean {

    /**
     * id : cetusCycle1559215740000
     * expiry : 2019-05-30T11:29:00.000Z
     * activation : 1559209740000
     * isDay : true
     * state : day
     * timeLeft : 1h 33m 13s
     * isCetus : true
     * shortString : 1h 33m to Night
     */

    private String id;
    private String expiry;
    private long activation;
    private boolean isDay;
    private String state;
    private String timeLeft;
    private boolean isCetus;
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

    public long getActivation() {
        return activation;
    }

    public void setActivation(long activation) {
        this.activation = activation;
    }

    public boolean isIsDay() {
        return isDay;
    }

    public void setIsDay(boolean isDay) {
        this.isDay = isDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean isIsCetus() {
        return isCetus;
    }

    public void setIsCetus(boolean isCetus) {
        this.isCetus = isCetus;
    }

    public String getShortString() {
        return shortString;
    }

    public void setShortString(String shortString) {
        this.shortString = shortString;
    }
}
