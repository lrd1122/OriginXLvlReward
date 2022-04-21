package gx.lrd1122.OriginXLvlReward.OriginXConfigCore;

import gx.lrd1122.OriginXLvlReward.OriginXAPI.OriginXWindowsAPI;
import gx.lrd1122.OriginXLvlReward.OriginXAPI.OxMinecraftAPI.OriginXMinecraftAPI;
import gx.lrd1122.OriginXLvlReward.OriginXLoggerCore.OxLoggerManager;
import gx.lrd1122.OriginXLvlReward.OriginXLvlReward;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore.OxLevelRewardClass;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore.OxLevelRewardManager;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore.OxPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class OxConfigManager {
    public static String prefix;
    public static String lang;

    public static File ConfigFile;
    public static YamlConfiguration ConfigYaml;
    public static File LangFile;
    public static YamlConfiguration LangYaml;
    public static List<File> rewardFiles;
    public static List<YamlConfiguration> rewardYamls;
    public static File dataParent;
    public static HashMap<String, YamlConfiguration> dataYamlMap;
    public static HashMap<String, File> dataFileMap;


    public static void initialize(){
        rewardFiles = new ArrayList<>();
        rewardYamls = new ArrayList<>();
        dataYamlMap = new HashMap<>();
        dataFileMap = new HashMap<>();
        OxLevelRewardManager.dataHashMap = new HashMap<>();
        OxLevelRewardManager.rewardHashMap = new HashMap<>();
        JavaPlugin plugin = OriginXLvlReward.plugin;
        File dataFolder = plugin.getDataFolder();
        //config
        boolean setLang = false;
        File configFile = new File(dataFolder, "config.yml");
        if(!configFile.exists()) {
            setLang = true;
        }
        OxConfigUtils.CreateDefaultFile(dataFolder, "config.yml", "config.yml");
        OxConfigManager.ConfigFile = configFile;
        OxConfigManager.ConfigYaml = YamlConfiguration.loadConfiguration(configFile);
        if(setLang){
            ConfigYaml.set("Language", OriginXWindowsAPI.getWindowsLanguage().name());
            try {
                ConfigYaml.save(ConfigFile);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        OxConfigManager.prefix = OriginXMinecraftAPI.ColorString(ConfigYaml.getString("Prefix"));
        OxConfigManager.lang = ConfigYaml.getString("Language");
        OxLoggerManager.info("&aLoaded config.yml √");
        //lang
        File langParent = new File(dataFolder, "Languages");
        if(!langParent.exists()) {
            langParent.mkdir();
            OxConfigUtils.CreateDefaultFile(langParent, lang + ".yml", "Languages\\" + lang + ".yml");
        }
        File langFile = new File(langParent, lang + ".yml");
        OxConfigManager.LangFile = langFile;
        OxConfigManager.LangYaml = YamlConfiguration.loadConfiguration(langFile);
        OxLoggerManager.info("&aLoaded " + lang + ".yml √");
        //rewards
        File rewardParent = new File(dataFolder, "Rewards");
        if(!rewardParent.exists()) {
            rewardParent.mkdir();
            OxConfigUtils.CreateDefaultFile(langParent, "Example.yml", "Rewards\\" + "Example.yml");
        }
        for(File file : rewardParent.listFiles()){
            YamlConfiguration rewardConfig = YamlConfiguration.loadConfiguration(file);
            rewardFiles.add(file);
            rewardYamls.add(rewardConfig);
            List<String> keys = new ArrayList<>(rewardConfig.getKeys(false));
            for(String key : keys) {
                ConfigurationSection section = rewardConfig.getConfigurationSection(key);
                        OxLevelRewardClass rewardClass = new OxLevelRewardClass();
                rewardClass.setKey(key);
                rewardClass.setPermission(section.getStringList("Permission"));
                rewardClass.setMinLevel(section.getInt("MinLevel"));
                rewardClass.setMaxLevel(section.getInt("MaxLevel"));
                rewardClass.setAutoReward(section.getBoolean("AutoReward"));
                rewardClass.setRewards(section.getStringList("Rewards"));
                rewardClass.setRewardSection(section.getConfigurationSection("Rewards"));
                OxLevelRewardManager.rewardHashMap.put(key, rewardClass);
            }
        }
        //playerdata
        File dataParent = new File(dataFolder, "PlayerData");
        OxConfigManager.dataParent = dataParent;
        if(!dataParent.exists()) {
            dataParent.mkdir();
        }
        if(dataParent.listFiles().length > 0) {
            for (File file : dataParent.listFiles()) {
                if(file.getName().endsWith(".yml")) {
                    OxPlayerData data = OxLevelRewardManager.loadData(file);
                    dataFileMap.put(YamlConfiguration.loadConfiguration(file).getName(), file);
                    dataYamlMap.put(data.getPlayer(), YamlConfiguration.loadConfiguration(file));
                }
            }
        }
    }
    public static String readConfigString(YamlConfiguration config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return "null";
        }
        else {
            return config.getString(key);
        }
    }
    public static String readConfigString(ConfigurationSection config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return "null";
        }
        else {
            return config.getString(key);
        }
    }
    public static List<String> readConfigStringList(ConfigurationSection config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return null;
        }
        else {
            return config.getStringList(key);
        }
    }
    public static boolean readConfigBoolean(YamlConfiguration config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return false;
        }
        else {
            return config.getBoolean(key);
        }
    }
    public static int readConfigInt(YamlConfiguration config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return 0;
        }
        else {
            return config.getInt(key);
        }
    }
    public static int readConfigInt(ConfigurationSection config, String key){
        if(config.get(key) == null){
            OxLoggerManager.warn("[%prefix%] Can't get from: " + config.getName() + " key: " + key + "'s value");
            return 0;
        }
        else {
            return config.getInt(key);
        }
    }
}
