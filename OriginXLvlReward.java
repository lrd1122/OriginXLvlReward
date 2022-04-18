package gx.lrd1122.OriginXLvlReward;

import gx.lrd1122.OriginXLvlReward.OriginXAPI.OxDependsAPI.OxPlaceholderAPI;
import gx.lrd1122.OriginXLvlReward.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLvlReward.OriginXEventCore.OxPlayerLevelListener;
import gx.lrd1122.OriginXLvlReward.OriginXLoggerCore.OxLoggerManager;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCommandCore.OxLvlRewardCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class OriginXLvlReward extends JavaPlugin {

    public static OriginXLvlReward instance;
    public static JavaPlugin plugin;
    public static boolean isPlaceholderAPI;


    @Override
    public void onEnable() {
        instance = this;
        plugin = this;
        load();
        Bukkit.getPluginManager().registerEvents(new OxPlayerLevelListener(), this);
        Bukkit.getPluginCommand("OriginXLvlReward").setExecutor(new OxLvlRewardCommand());
        Bukkit.getPluginCommand("OriginXLvlReward").setTabCompleter(new OxLvlRewardCommand());
        OxLoggerManager.info("Author: lrd1122 Discord:lrd1122#9401");
        OxLoggerManager.info("Welcome to use OriginXLvlReward Minecraft Plugin");
        OxLoggerManager.info("To get some supports, join our discord https://discord.gg/Ytuf2wAg8v");
        OxLoggerManager.info("QQ 交流讨论群 788461181 请勿发送任何广告");
    }

    public static void load(){
        OxConfigManager.initialize();
        OxPlaceholderAPI.isPlaceholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");


    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
