package com.karas.wftoolkit.Retrofit2.Bean;

import java.util.List;

/**
 * Created by Hongliang Luo on 2019/5/31.
 **/
public class EventsBean {

    private String id;
    private String activation;
    private String startString;
    private String expiry;
    private boolean active;
    private String description;
    private boolean expired;
    private String asString;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getStartString() {
        return startString;
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getAsString() {
        return asString;
    }

    public void setAsString(String asString) {
        this.asString = asString;
    }

}
