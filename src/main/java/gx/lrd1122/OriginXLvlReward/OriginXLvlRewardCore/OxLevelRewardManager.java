package gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore;

import gx.lrd1122.OriginXLvlReward.OriginXAPI.OriginXYamlAPI;
import gx.lrd1122.OriginXLvlReward.OriginXAPI.OxGUIAPI.OaAnalysisFunction;
import gx.lrd1122.OriginXLvlReward.OriginXConfigCore.OxConfigManager;
import gx.lrd1122.OriginXLvlReward.OriginXLoggerCore.OxLoggerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OxLevelRewardManager {
    public static HashMap<String, OxLevelRewardClass> rewardHashMap;
    public static HashMap<String, OxPlayerData> dataHashMap;
    public static void rewardPlayer(Player player, String name, boolean notify) {
        if (!rewardHashMap.containsKey(name)) {
            OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.UnknownReward"));
            return;
        }
        OxLevelRewardClass rewardClass = rewardHashMap.get(name);
        OxPlayerData data = getData(player);
        if (data.getRewardValues().containsKey(name)) {
            if (data.getRewardValues().get(name) > rewardClass.getRewardlimit()) {
                if (notify)
                    OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.RewardLimit"));
                return;
            }
        }
        if(rewardClass.getPermission() != null) {
            for (String s : rewardClass.getPermission()) {
                if (!player.hasPermission(s)) {
                    if (notify)
                        OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.NoPermission"));
                    return;
                }
            }
        }
        if (player.getLevel() > rewardClass.getMaxLevel()) {
            if (notify)
                OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.OutofLevel"));
            return;
        }
        if (player.getLevel() < rewardClass.getMinLevel()) {
            if (notify)
                OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.LowofLevel"));
            return;
        }
        OaAnalysisFunction.Section(player, rewardClass.getRewardSection());
        addData(player, rewardClass.getKey());

    }
    public static OxPlayerData getData(Player player){
        return dataHashMap.containsKey(player.getName()) ? dataHashMap.get(player.getName()) : createData(player);
    }
    public static void addData(Player player, String name){
        OxPlayerData data = null;
        if(!dataHashMap.containsKey(player.getName())){
            dataHashMap.put(player.getName(), createData(player));
        }
        setData(player, name, 1, true);
    }
    public static OxPlayerData createData(Player player) {
        OxPlayerData data = new OxPlayerData(player.getName());
        File file = new File(OxConfigManager.dataParent, player.getName() + ".yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        yaml.set("player", player.getName());
        yaml.set("rewards", new ArrayList<String>());
        data.setRewardValues(new HashMap<>());
        dataHashMap.put(player.getName(), data);
        OxConfigManager.dataFileMap.put(yaml.getName(), file);
        OxConfigManager.dataYamlMap.put(player.getName(), yaml);
        try {
            yaml.save(OxConfigManager.dataFileMap.get(yaml.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static void setData(Player player, String s , Integer value, boolean add) {
        OxPlayerData data = null;
        if (!dataHashMap.containsKey(player.getName())) {
            dataHashMap.put(player.getName(), createData(player));
        }
        data = OxLevelRewardManager.dataHashMap.get(player.getName());
        data.getRewardValues().put(s, data.getRewardValues().containsKey(s)
                ? (add ? data.getRewardValues().get(s) + 1 : value) : 1);
        YamlConfiguration config = OxConfigManager.dataYamlMap.get(player.getName());
        config.set("rewards", dataToStr(data));
        try {
            config.save(OxConfigManager.dataFileMap.get(config.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<String> dataToStr(OxPlayerData data){
        List<String> s = new ArrayList<>();
        for(int i = 0 ; i < data.getRewardValues().size(); i++){
            s.add(new ArrayList<>(data.getRewardValues().keySet()).get(i) + ","
            + new ArrayList<>(data.getRewardValues().values()).get(i));
        }
        return s;
    }
    public static OxPlayerData loadData(File file){
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        OxPlayerData data = new OxPlayerData(config.getString("player"));
        if(config.get("rewards") != null){
            HashMap<String, Integer> map = new HashMap<>();
            for(String s : config.getStringList("rewards")){
                String[] value = s.split(",");
                map.put(value[0], Integer.valueOf(value[1]));
            }
            data.setRewardValues(map);
        }
        dataHashMap.put(config.getString("player"), data);
        return data;
    }

}
