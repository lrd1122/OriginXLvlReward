package gx.lrd1122.OriginXLvlReward.OriginXEventCore;

import gx.lrd1122.OriginXLvlReward.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLvlReward.OriginXLvlReward;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore.OxLevelRewardClass;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore.OxLevelRewardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OxPlayerLevelListener implements Listener {
    @EventHandler
    public void onLevelUp(PlayerLevelChangeEvent event){
            for (OxLevelRewardClass rewardClass : OxLevelRewardManager.rewardHashMap.values()) {
                OxLevelRewardManager.rewardPlayer(event.getPlayer(), rewardClass.getKey(), false);
            }
        }
}
