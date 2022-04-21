package gx.lrd1122.OriginXLvlReward.OriginXConfigCore;



import gx.lrd1122.OriginXLvlReward.OriginXLvlReward;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class OxConfigUtils {
    public static YamlConfiguration FileToYaml(String parent, String name) {
        File file = new File(parent, name);
        if(file.exists()) {
            return YamlConfiguration.loadConfiguration(file);
        }
        return null;
    }

    public static YamlConfiguration FileToYaml(File parent, String name) {
        File file = new File(parent, name);
        if(file.exists()) {
            return YamlConfiguration.loadConfiguration(file);
        }
        return null;
    }

    public static void CreateDefaultFile(File parent, String name, String toCreate) {
        File file = new File(parent, name);
        if(!file.exists()){
            OriginXLvlReward.plugin.saveResource(toCreate, true);
        }
    }
    public static void CreateDefaultFile(String parent, String name, String toCreate) {
        File file = new File(parent, name);
        if(!file.exists()){
            OriginXLvlReward.plugin.saveResource(toCreate, true);
        }
    }
}
