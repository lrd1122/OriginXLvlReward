package gx.lrd1122.OriginXLvlReward.OriginXDependCore;

import gx.lrd1122.OriginXLvlReward.OriginXLoggerCore.OxLoggerManager;
import gx.lrd1122.OriginXLvlReward.OriginXLvlReward;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore.OxLevelRewardManager;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore.OxPlayerData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class OxPlaceholderAPIHook extends PlaceholderExpansion {
    private final OriginXLvlReward plugin;
    public OxPlaceholderAPIHook(OriginXLvlReward reward){
        plugin = reward;
    }
    @Override
    public String getIdentifier() {
        return "OriginXLvlReward";
    }

    @Override
    public String getAuthor() {
        return "lrd1122";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }


    @Override
    public String onRequest(OfflinePlayer player, String params) {
        String[] strings = params.split("_");
        if(strings.length > 1){
            if(OxLevelRewardManager.dataHashMap.containsKey(strings[0])){
                OxPlayerData data = OxLevelRewardManager.dataHashMap.get(strings[0]);
                if(data.getRewardValues().containsKey(strings[1])){
                    return String.valueOf(data.getRewardValues().get(strings[1]));
                }
                else{
                    return "0";
                }
            }
        }
        return null;
    }

    @Override
    public boolean persist() {
        return true;
    }
}
