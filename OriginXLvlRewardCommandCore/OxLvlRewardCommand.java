package gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCommandCore;

import gx.lrd1122.OriginXLvlReward.OriginXAPI.OriginXYamlAPI;
import gx.lrd1122.OriginXLvlReward.OriginXLoggerCore.OxLoggerManager;
import gx.lrd1122.OriginXLvlReward.OriginXLvlReward;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore.OxLevelRewardClass;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore.OxLevelRewardManager;
import gx.lrd1122.OriginXLvlReward.OriginXLvlRewardCore.OxPlayerData;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class OxLvlRewardCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length > 0){
                if(args[0].equalsIgnoreCase("help") && player.hasPermission("oxlr.help")){
                    OxLoggerManager.sendMessage(sender, OriginXYamlAPI.getLangStringList("Commands.Help"));
                    return true;
                }
                if(args[0].equalsIgnoreCase("reward")){
                    if(args.length < 2){
                        OxLoggerManager.sendMessage(sender, OriginXYamlAPI.getLangString("Errors.UnknownReward"));
                        return true;
                    }
                    if(player.hasPermission("oxlr.reward." + args[2]))
                        OxLevelRewardManager.rewardPlayer(player, args[1], true);
                        return true;
                }
                if(args[0].equalsIgnoreCase("settime") && player.hasPermission("oxlr.settime")){
                    if(args.length < 2){
                        OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.UnknownTarget"));
                        return true;
                    }
                    if(args.length < 4){
                        OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.UnknownInput"));
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    String key = args[2];
                    int amount = Integer.parseInt(args[3]);
                    OxLevelRewardManager.setData(target, key, amount, false);
                    OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Commands.SetTime")
                            .replace("%Player%", player.getName())
                            .replace("%Key%", key)
                            .replace("%Number%", String.valueOf(OxLevelRewardManager.getData(player).getRewardValues().get(key))));;
                    return true;
                }
                if(args[0].equalsIgnoreCase("addtime") && player.hasPermission("oxlr.addtime")){
                    if(args.length < 2){
                        OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.UnknownTarget"));
                        return true;
                    }
                    if(args.length < 4){
                        OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.UnknownInput"));
                        return true;
                    }
                    Player target = Bukkit.getPlayer(args[1]);
                    String key = args[2];
                    int amount = Integer.parseInt(args[3]);
                    OxLevelRewardManager.setData(target, key, amount, true);
                    OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Commands.AddTime")
                            .replace("%Player%", target.getName())
                            .replace("%Key%", key)
                            .replace("%Number%", String.valueOf(OxLevelRewardManager.getData(player).getRewardValues().get(key))));
                    return true;
                }
                if(args[0].equalsIgnoreCase("seetime") && player.hasPermission("oxlr.seetime")){
                    if(args.length < 2) {
                        OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.UnknownTarget"));
                        return true;
                    }
                    OxPlayerData data = OxLevelRewardManager.getData(player);
                    Player target = Bukkit.getPlayer(args[1]);
                    for(OxLevelRewardClass rewardClass : OxLevelRewardManager.rewardHashMap.values()) {
                        String key = rewardClass.getKey();
                            OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Commands.SeeTime")
                                    .replace("%Player%", target.getName())
                                    .replace("%Key%", key)
                                    .replace("%Number%", String.valueOf(
                                            OxLevelRewardManager.getData(player).getRewardValues().containsKey(rewardClass.getKey()) ?
                                                    OxLevelRewardManager.getData(player).getRewardValues().get(key) : 0)));
                    }
                    return true;
                }
                if(args[0].equalsIgnoreCase("reload") && player.hasPermission("oxlr.reload")){
                    OriginXLvlReward.load();
                    OxLoggerManager.sendMessage(player, OriginXYamlAPI.getLangString("Errors.Reload"));
                    return true;
                }
            }
        }
        else{
            if(args.length > 0){
                if(args[0].equalsIgnoreCase("help")){
                    OxLoggerManager.sendMessage(sender, OriginXYamlAPI.getLangStringList("Commands.Help"));
                }
            }
        }
        OxLoggerManager.sendMessage(sender, OriginXYamlAPI.getLangStringList("Commands.Help"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
