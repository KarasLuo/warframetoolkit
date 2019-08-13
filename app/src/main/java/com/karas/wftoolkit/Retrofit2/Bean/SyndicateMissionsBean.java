package com.karas.wftoolkit.Retrofit2.Bean;

import java.util.List;

/**
 * Created by Hongliang Luo on 2019/5/31.
 **/
public class SyndicateMissionsBean {
    /**
     * id : 1559272733435Ostrons
     * activation : 2019-05-31T00:48:54.561Z
     * startString : -57m 42s
     * expiry : 2019-05-31T03:18:53.435Z
     * active : true
     * syndicate : Ostrons
     * nodes : []
     * jobs : [{"id":"AttritionBountySab1559272733435","rewardPool":["Vitality","200X Plastids","1,500 Credits Cache","50 Endo","15X Nistlepod","Gara Chassis BP","Point Blank","Intensify","2X Gallium"],"type":"Sabotage the Enemy Supply Lines","enemyLevels":[5,15],"standingStages":[390,390,390]},{"id":"ReclamationBountyCache1559272733435","rewardPool":["Point Strike","300X Circuits","2,500 Credits Cache","100 Endo","Gara Systems BP","Enhanced Durability","Grim Fury","Lith P2 Relic","2X Orokin Cell"],"type":"Find the Hidden Artifact","enemyLevels":[10,30],"standingStages":[620,620,620]},{"id":"RescueBountyResc1559272733435","rewardPool":["Augur Pact","Naramon Lens","Zenurik Lens","200 Endo","Gara Neuroptics BP","Vigilante Fervor","Revenant Systems BP","Meso E2 Relic","Gladiator Vice"],"type":"Search and Rescue","enemyLevels":[20,40],"standingStages":[640,640,640,940]},{"id":"ReclamationBountyTheft1559272733435","rewardPool":["Augur Message","100X Kuva","Naramon Lens","300 Endo","Cetus Wisp","Vigilante Pursuit","Revenant Chassis BP","Neo C1 Relic","Gladiator Finesse"],"type":"Reclaim the Stolen Artifact","enemyLevels":[30,50],"standingStages":[630,630,630,630,1230]},{"id":"AttritionBountyLib1559272733435","rewardPool":["5X Breath Of The Eidolon","400 Endo","2X Cetus Wisp","300X Kuva","Axi T2 Relic","Furax Wraith BP","Twirling Spire","Eidolon Lens BP","Revenant Neuroptics BP"],"type":"Weaken the Grineer Foothold","enemyLevels":[40,60],"standingStages":[680,680,680,680,1330]}]
     * eta : 1h 32m 16s
     */

    private String id;
    private String activation;
    private String startString;
    private String expiry;
    private boolean active;
    private String syndicate;
    private String eta;
    private List<?> nodes;
    private List<JobsBean> jobs;

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

    public String getSyndicate() {
        return syndicate;
    }

    public void setSyndicate(String syndicate) {
        this.syndicate = syndicate;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public List<?> getNodes() {
        return nodes;
    }

    public void setNodes(List<?> nodes) {
        this.nodes = nodes;
    }

    public List<JobsBean> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobsBean> jobs) {
        this.jobs = jobs;
    }

    public static class JobsBean {
        /**
         * id : AttritionBountySab1559272733435
         * rewardPool : ["Vitality","200X Plastids","1,500 Credits Cache","50 Endo","15X Nistlepod","Gara Chassis BP","Point Blank","Intensify","2X Gallium"]
         * type : Sabotage the Enemy Supply Lines
         * enemyLevels : [5,15]
         * standingStages : [390,390,390]
         */

        private String id;
        private String type;
        private List<String> rewardPool;
        private List<Integer> enemyLevels;
        private List<Integer> standingStages;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getRewardPool() {
            return rewardPool;
        }

        public void setRewardPool(List<String> rewardPool) {
            this.rewardPool = rewardPool;
        }

        public List<Integer> getEnemyLevels() {
            return enemyLevels;
        }

        public void setEnemyLevels(List<Integer> enemyLevels) {
            this.enemyLevels = enemyLevels;
        }

        public List<Integer> getStandingStages() {
            return standingStages;
        }

        public void setStandingStages(List<Integer> standingStages) {
            this.standingStages = standingStages;
        }
    }
}
