package me.epicgodmc.royalsagatokens.utilities;

import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MessageManager {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private FileConfiguration lang = plugin.files.getLangConfig();


    public String prefix = applyCC(lang.getString("pluginPrefix"));
    public String adminPrefix = applyCC(lang.getString("adminMessagePrefix"));

    public String getMessage(String input) {
        return applyCC(prefix + lang.getString("messages." + input));
    }

    public void sendAdminMessage(String input) {
        for (Player targets : Bukkit.getOnlinePlayers()) {
            if (targets.hasPermission("tokens.admin")) {
                targets.sendMessage(applyCC(adminPrefix + input));
            }
        }
    }


    public void sendUsage(CommandSender sender, String type) {
        for (String str : lang.getStringList("usages." + type)) {
            sender.sendMessage(applyCC(str));
        }
    }

    public String applyCC(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }


}
