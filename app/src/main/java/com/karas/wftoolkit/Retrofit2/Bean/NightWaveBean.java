package com.karas.wftoolkit.Retrofit2.Bean;

import java.util.List;

/**
 * Created by Hongliang Luo on 2019/5/31.
 **/
public class NightWaveBean {

    /**
     * id : nightwave1562371200000
     * activation : 2019-06-10T00:00:00.000Z
     * startString : -8d 9h 16m 23s
     * expiry : 2019-07-06T00:00:00.000Z
     * active : true
     * season : 1
     * tag : Radio Legion Intermission Syndicate
     * phase : 0
     * params : {}
     * possibleChallenges : []
     * activeChallenges : [{"id":"1560902400000seasondailykillenemieswithfire1","activation":"2019-06-16T00:00:00.000Z","startString":"-2d 9h 16m 23s","expiry":"2019-06-19T00:00:00.000Z","active":true,"isDaily":true,"isElite":false,"desc":"Kill 150 Enemies with Heat Damage","title":"Arsonist","reputation":1000},{"id":"1560988800000seasondailykillenemieswithmagnetic1","activation":"2019-06-17T00:00:00.000Z","startString":"-1d 9h 16m 23s","expiry":"2019-06-20T00:00:00.000Z","active":true,"isDaily":true,"isElite":false,"desc":"Kill 150 Enemies with Magnetic Damage","title":"Attractive","reputation":1000},{"id":"1561075200000seasondailybulletjump1","activation":"2019-06-18T00:00:00.000Z","startString":"-9h 16m 23s","expiry":"2019-06-21T00:00:00.000Z","active":true,"isDaily":true,"isElite":false,"desc":"Bullet Jump 150 times","title":"Trampoline","reputation":1000},{"id":"1561334400000seasonweeklycatchrareplainsfish1","activation":"2019-06-17T00:00:00.000Z","startString":"-1d 9h 16m 23s","expiry":"2019-06-24T00:00:00.000Z","active":true,"isElite":false,"desc":"Catch 6 Rare Fish in the Plains of Eidolon","title":"Earth Fisher","reputation":3000},{"id":"1561334400000seasonweeklycompleterescue1","activation":"2019-06-17T00:00:00.000Z","startString":"-1d 9h 16m 23s","expiry":"2019-06-24T00:00:00.000Z","active":true,"isElite":false,"desc":"Complete 3 Rescue missions","title":"Rescuer","reputation":3000},{"id":"1561334400000seasonweeklygildmodular1","activation":"2019-06-17T00:00:00.000Z","startString":"-1d 9h 16m 23s","expiry":"2019-06-24T00:00:00.000Z","active":true,"isElite":false,"desc":"Gild 1 Modular Items","title":"Gilded","reputation":3000},{"id":"1561334400000seasonweeklyplainsbounties1","activation":"2019-06-17T00:00:00.000Z","startString":"-1d 9h 16m 23s","expiry":"2019-06-24T00:00:00.000Z","active":true,"isElite":false,"desc":"Complete 8 Bounties in the Plains of Eidolon","title":"Earth Bounty Hunter","reputation":3000},{"id":"1561334400000seasonweeklyunlockdragonvaults1","activation":"2019-06-17T00:00:00.000Z","startString":"-1d 9h 16m 23s","expiry":"2019-06-24T00:00:00.000Z","active":true,"isElite":false,"desc":"Unlock 4 Orokin Vaults","title":"Vault Looter","reputation":3000},{"id":"1561334400000seasonweeklyhardelitesanctuaryonslaught1","activation":"2019-06-17T00:00:00.000Z","startString":"-1d 9h 16m 23s","expiry":"2019-06-24T00:00:00.000Z","active":true,"isElite":true,"desc":"Complete 3 waves of Elite Sanctuary Onslaught","title":"Elite Test Subject","reputation":5000},{"id":"1561334400000seasonweeklyhardfriendsdefense1","activation":"2019-06-17T00:00:00.000Z","startString":"-1d 9h 16m 23s","expiry":"2019-06-24T00:00:00.000Z","active":true,"isElite":true,"desc":"Complete a Defense mission reaching at least wave 40, while playing with a friend or clanmate","title":"Defense with Friends","reputation":5000}]
     * rewardTypes : ["credits"]
     */

    private String id;
    private String activation;
    private String startString;
    private String expiry;
    private boolean active;
    private int season;
    private String tag;
    private int phase;
    private ParamsBean params;
    private List<?> possibleChallenges;
    private List<ActiveChallengesBean> activeChallenges;
    private List<String> rewardTypes;

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

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public List<?> getPossibleChallenges() {
        return possibleChallenges;
    }

    public void setPossibleChallenges(List<?> possibleChallenges) {
        this.possibleChallenges = possibleChallenges;
    }

    public List<ActiveChallengesBean> getActiveChallenges() {
        return activeChallenges;
    }

    public void setActiveChallenges(List<ActiveChallengesBean> activeChallenges) {
        this.activeChallenges = activeChallenges;
    }

    public List<String> getRewardTypes() {
        return rewardTypes;
    }

    public void setRewardTypes(List<String> rewardTypes) {
        this.rewardTypes = rewardTypes;
    }

    public static class ParamsBean {
    }

    public static class ActiveChallengesBean {
        /**
         * id : 1560902400000seasondailykillenemieswithfire1
         * activation : 2019-06-16T00:00:00.000Z
         * startString : -2d 9h 16m 23s
         * expiry : 2019-06-19T00:00:00.000Z
         * active : true
         * isDaily : true
         * isElite : false
         * desc : Kill 150 Enemies with Heat Damage
         * title : Arsonist
         * reputation : 1000
         */

        private String id;
        private String activation;
        private String startString;
        private String expiry;
        private boolean active;
        private boolean isDaily;
        private boolean isElite;
        private String desc;
        private String title;
        private int reputation;

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

        public boolean isIsDaily() {
            return isDaily;
        }

        public void setIsDaily(boolean isDaily) {
            this.isDaily = isDaily;
        }

        public boolean isIsElite() {
            return isElite;
        }

        public void setIsElite(boolean isElite) {
            this.isElite = isElite;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getReputation() {
            return reputation;
        }

        public void setReputation(int reputation) {
            this.reputation = reputation;
        }
    }
}
