package com.karas.wftoolkit.Retrofit2.Bean;

/**
 * Created by Hongliang Luo on 2019/5/30.
 **/
public class EarthCycleBean {

    /**
     * id : earthCycle1559217600000
     * expiry : 2019-05-30T12:00:00.628Z
     * activation : 2019-05-30T08:00:00.628Z
     * isDay : true
     * state : day
     * timeLeft : 2h 2m 13s
     */

    private String id;
    private String expiry;
    private String activation;
    private boolean isDay;
    private String state;
    private String timeLeft;

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

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
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
}
