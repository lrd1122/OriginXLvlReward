package gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class OxPlayerData {

    private String player;
    private HashMap<String, Integer> rewardValues;
    public OxPlayerData(String player){
        this.player = player;
        rewardValues = new HashMap<>();
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public HashMap<String, Integer> getRewardValues() {
        return rewardValues;
    }

    public void setRewardValues(HashMap<String, Integer> rewardValues) {
        this.rewardValues = rewardValues;
    }
    public void setValue(String s, Integer i){
        this.rewardValues.put(s, i);
    }
}
