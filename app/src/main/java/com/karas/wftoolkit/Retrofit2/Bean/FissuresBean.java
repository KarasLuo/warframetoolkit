package com.karas.wftoolkit.Retrofit2.Bean;

/**
 * Created by Hongliang Luo on 2019/5/31.
 **/
public class FissuresBean {
    /**
     * id : 5cf07025e0f28d2b4a2e867a
     * activation : 2019-05-31T00:07:01.836Z
     * startString : -1h 35m 35s
     * expiry : 2019-05-31T01:46:23.318Z
     * active : true
     * node : Ani (Void)
     * missionType : Survival
     * enemy : Orokin
     * tier : Axi
     * tierNum : 4
     * expired : false
     * eta : 3m 45s
     */

    private String id;
    private String activation;
    private String startString;
    private String expiry;
    private boolean active;
    private String node;
    private String missionType;
    private String enemy;
    private String tier;
    private int tierNum;
    private boolean expired;
    private String eta;

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

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getMissionType() {
        return missionType;
    }

    public void setMissionType(String missionType) {
        this.missionType = missionType;
    }

    public String getEnemy() {
        return enemy;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public int getTierNum() {
        return tierNum;
    }

    public void setTierNum(int tierNum) {
        this.tierNum = tierNum;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}
