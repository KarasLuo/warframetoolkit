package com.karas.wftoolkit.Retrofit2.Bean;

import java.util.List;

/**
 * Created by Hongliang Luo on 2019/5/31.
 **/
public class InvasionsBean {

    /**
     * id : 5cef935d2c15fd0d9c84d869
     * activation : 2019-05-30T08:25:01.727Z
     * startString : -17h 15m 35s
     * node : Neso (Neptune)
     * desc : Infested Outbreak
     * attackerReward : {"items":[],"countedItems":[],"credits":0,"asString":"","itemString":"","thumbnail":"https://i.imgur.com/JCKyUXJ.png","color":15844367}
     * attackingFaction : Infested
     * defenderReward : {"items":[],"countedItems":[{"count":1,"type":"Mutalist Alad V Nav Coordinate"}],"credits":0,"asString":"Mutalist Alad V Nav Coordinate","itemString":"Mutalist Alad V Nav Coordinate","thumbnail":"https://i.imgur.com/96AWqr8.png","color":158519}
     * defendingFaction : Corpus
     * vsInfestation : true
     * count : -37201
     * requiredRuns : 37000
     * completion : -0.543243243243241
     * completed : true
     * eta : -5m 35s
     * rewardTypes : ["mutalist"]
     */

    private String id;
    private String activation;
    private String startString;
    private String node;
    private String desc;
    private AttackerRewardBean attackerReward;
    private String attackingFaction;
    private DefenderRewardBean defenderReward;
    private String defendingFaction;
    private boolean vsInfestation;
    private int count;
    private int requiredRuns;
    private double completion;
    private boolean completed;
    private String eta;
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

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public AttackerRewardBean getAttackerReward() {
        return attackerReward;
    }

    public void setAttackerReward(AttackerRewardBean attackerReward) {
        this.attackerReward = attackerReward;
    }

    public String getAttackingFaction() {
        return attackingFaction;
    }

    public void setAttackingFaction(String attackingFaction) {
        this.attackingFaction = attackingFaction;
    }

    public DefenderRewardBean getDefenderReward() {
        return defenderReward;
    }

    public void setDefenderReward(DefenderRewardBean defenderReward) {
        this.defenderReward = defenderReward;
    }

    public String getDefendingFaction() {
        return defendingFaction;
    }

    public void setDefendingFaction(String defendingFaction) {
        this.defendingFaction = defendingFaction;
    }

    public boolean isVsInfestation() {
        return vsInfestation;
    }

    public void setVsInfestation(boolean vsInfestation) {
        this.vsInfestation = vsInfestation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRequiredRuns() {
        return requiredRuns;
    }

    public void setRequiredRuns(int requiredRuns) {
        this.requiredRuns = requiredRuns;
    }

    public double getCompletion() {
        return completion;
    }

    public void setCompletion(double completion) {
        this.completion = completion;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public List<String> getRewardTypes() {
        return rewardTypes;
    }

    public void setRewardTypes(List<String> rewardTypes) {
        this.rewardTypes = rewardTypes;
    }

    public static class AttackerRewardBean {
        /**
         * items : []
         * countedItems : []
         * credits : 0
         * asString :
         * itemString :
         * thumbnail : https://i.imgur.com/JCKyUXJ.png
         * color : 15844367
         */

        private int credits;
        private String asString;
        private String itemString;
        private String thumbnail;
        private int color;
        private List<?> items;
        private List<?> countedItems;

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

        public String getAsString() {
            return asString;
        }

        public void setAsString(String asString) {
            this.asString = asString;
        }

        public String getItemString() {
            return itemString;
        }

        public void setItemString(String itemString) {
            this.itemString = itemString;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public List<?> getItems() {
            return items;
        }

        public void setItems(List<?> items) {
            this.items = items;
        }

        public List<?> getCountedItems() {
            return countedItems;
        }

        public void setCountedItems(List<?> countedItems) {
            this.countedItems = countedItems;
        }
    }

    public static class DefenderRewardBean {
        /**
         * items : []
         * countedItems : [{"count":1,"type":"Mutalist Alad V Nav Coordinate"}]
         * credits : 0
         * asString : Mutalist Alad V Nav Coordinate
         * itemString : Mutalist Alad V Nav Coordinate
         * thumbnail : https://i.imgur.com/96AWqr8.png
         * color : 158519
         */

        private int credits;
        private String asString;
        private String itemString;
        private String thumbnail;
        private int color;
        private List<?> items;
        private List<CountedItemsBean> countedItems;

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

        public String getAsString() {
            return asString;
        }

        public void setAsString(String asString) {
            this.asString = asString;
        }

        public String getItemString() {
            return itemString;
        }

        public void setItemString(String itemString) {
            this.itemString = itemString;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public List<?> getItems() {
            return items;
        }

        public void setItems(List<?> items) {
            this.items = items;
        }

        public List<CountedItemsBean> getCountedItems() {
            return countedItems;
        }

        public void setCountedItems(List<CountedItemsBean> countedItems) {
            this.countedItems = countedItems;
        }

        public static class CountedItemsBean {
            /**
             * count : 1
             * type : Mutalist Alad V Nav Coordinate
             */

            private int count;
            private String type;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
