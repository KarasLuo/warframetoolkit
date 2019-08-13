package com.karas.wftoolkit.Retrofit2.Bean;

import java.util.List;

/**
 * Created by Hongliang Luo on 2019/5/31.
 **/
public class SortieBean {

    /**
     * id : 5ceffe026fd1db5eeb86d3ab
     * activation : 2019-05-30T16:00:01.999Z
     * startString : -9h 53m 35s
     * expiry : 2019-05-31T15:59:00.000Z
     * active : true
     * rewardPool : Sortie Rewards
     * variants : [{"boss":"Deprecated","planet":"Deprecated","missionType":"Rescue","modifier":"Augmented Enemy Armor","modifierDescription":"Enemies have Improved/Added armor. Corrosive Projection effects are halved.","node":"Grimaldi (Lua)"},{"boss":"Deprecated","planet":"Deprecated","missionType":"Defense","modifier":"Eximus Stronghold","modifierDescription":"Eximus units have a much higher spawn rate in this mission. Some of their auras stack.","node":"Umbriel (Uranus)"},{"boss":"Deprecated","planet":"Deprecated","missionType":"Assassination","modifier":"Energy Reduction","modifierDescription":"Maximum Warframe Energy capacity is quartered. Energy Siphon is less effective.","node":"Oro (Earth)"}]
     * boss : Councilor Vay Hek
     * faction : Grineer
     * expired : false
     * eta : 14h 5m 22s
     */

    private String id;
    private String activation;
    private String startString;
    private String expiry;
    private boolean active;
    private String rewardPool;
    private String boss;
    private String faction;
    private boolean expired;
    private String eta;
    private List<VariantsBean> variants;

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

    public String getRewardPool() {
        return rewardPool;
    }

    public void setRewardPool(String rewardPool) {
        this.rewardPool = rewardPool;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
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

    public List<VariantsBean> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantsBean> variants) {
        this.variants = variants;
    }

    public static class VariantsBean {
        /**
         * boss : Deprecated
         * planet : Deprecated
         * missionType : Rescue
         * modifier : Augmented Enemy Armor
         * modifierDescription : Enemies have Improved/Added armor. Corrosive Projection effects are halved.
         * node : Grimaldi (Lua)
         */

        private String boss;
        private String planet;
        private String missionType;
        private String modifier;
        private String modifierDescription;
        private String node;

        public String getBoss() {
            return boss;
        }

        public void setBoss(String boss) {
            this.boss = boss;
        }

        public String getPlanet() {
            return planet;
        }

        public void setPlanet(String planet) {
            this.planet = planet;
        }

        public String getMissionType() {
            return missionType;
        }

        public void setMissionType(String missionType) {
            this.missionType = missionType;
        }

        public String getModifier() {
            return modifier;
        }

        public void setModifier(String modifier) {
            this.modifier = modifier;
        }

        public String getModifierDescription() {
            return modifierDescription;
        }

        public void setModifierDescription(String modifierDescription) {
            this.modifierDescription = modifierDescription;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }
    }
}
