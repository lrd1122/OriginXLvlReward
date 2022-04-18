package gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore;

import java.util.ArrayList;
import java.util.List;

public class OxLevelRewardClass {

    private String key;
    private List<String> permission;
    private int maxLevel;
    private int minLevel;
    private boolean autoReward;
    private int rewardlimit;
    private List<String> rewards;

    public OxLevelRewardClass(){
        permission = new ArrayList<>();
        rewards = new ArrayList<>();
    }

    public int getRewardlimit() {
        return rewardlimit;
    }

    public void setRewardlimit(int rewardlimit) {
        this.rewardlimit = rewardlimit;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public boolean isAutoReward() {
        return autoReward;
    }

    public List<String> getPermission() {
        return permission;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public void setAutoReward(boolean autoReward) {
        this.autoReward = autoReward;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }

    public void setRewards(List<String> rewards) {
        this.rewards = rewards;
    }

}
