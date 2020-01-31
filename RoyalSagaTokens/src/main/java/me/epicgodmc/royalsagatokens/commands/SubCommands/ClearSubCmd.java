package me.epicgodmc.royalsagatokens.commands.SubCommands;

import me.epicgodmc.royalsagatokens.Objects.SubCommand;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.TokenPlayers;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import me.epicgodmc.royalsagatokens.utilities.MsgFormatter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class ClearSubCmd extends SubCommand {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission("tokens.admin")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (target != null) {
                    if (clearFileTokens(target.getUniqueId()))
                        sender.sendMessage(new MsgFormatter(mm.getMessage("tokenClearSucceedFile")).formatPlayer(target.getName()).toString());
                    if (clearMemoryTokens(target.getUniqueId()))
                        sender.sendMessage(new MsgFormatter(mm.getMessage("tokenClearSucceedMemory")).formatPlayer(target.getName()).toString());

                } else {
                    sender.sendMessage(mm.getMessage("playerNotFound").replace("player%", args[0]));
                }
            } else {
                sender.sendMessage(mm.getMessage("noPermission"));
            }
        } else {
            sender.sendMessage(mm.getMessage("invalidArgLenght").replace("%count%", "1"));
        }
    }

    public boolean clearFileTokens(UUID uuid) {
        FileConfiguration dataFile = plugin.files.getPlayerDataConfig();

        if (dataFile.isSet("data." + uuid.toString() + ".tokens")) {
            dataFile.set("data." + uuid.toString() + ".tokens", 0);
            return true;
        }
        return false;

    }

    public boolean clearMemoryTokens(UUID uuid) {
        if (TokenPlayers.getInstance().isLoaded(uuid)) {
            TokenPlayers.getInstance().getByUUID(uuid).setTokens(0);
            return true;
        }
        return false;
    }


    @Override
    public String name() {
        return plugin.cmdRoot.clear;
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
