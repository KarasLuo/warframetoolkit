package com.karas.wftoolkit.Retrofit2.Bean;

import java.util.List;

/**
 * Created by Hongliang Luo on 2019/5/31.
 **/
public class VoidTraderBean {
    /**
     * id : 5b3f70c1be87e4524f04d5ec
     * activation : 2019-05-31T13:00:00.000Z
     * startString : 11h 4m 22s
     * expiry : 2019-06-02T13:00:00.000Z
     * active : false
     * character : Baro Ki'Teer
     * location : Orcus Relay (Pluto)
     * inventory : []
     * psId : 5b3f70c1be87e4524f04d5ec0
     * endString : 2d 11h 4m 22s
     */

    private String id;
    private String activation;
    private String startString;
    private String expiry;
    private boolean active;
    private String character;
    private String location;
    private String psId;
    private String endString;
    private List<?> inventory;

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

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPsId() {
        return psId;
    }

    public void setPsId(String psId) {
        this.psId = psId;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

    public List<?> getInventory() {
        return inventory;
    }

    public void setInventory(List<?> inventory) {
        this.inventory = inventory;
    }
}
