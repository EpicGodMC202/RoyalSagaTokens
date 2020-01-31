package me.epicgodmc.royalsagatokens.commands.SubCommands;

import me.epicgodmc.royalsagatokens.Objects.SubCommand;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import org.bukkit.command.CommandSender;

public class HelpSubCmd extends SubCommand {

    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender.hasPermission("tokens.admin")) {
            mm.sendUsage(sender, "admin");
        } else if (sender.hasPermission("tokens.access")) {
            mm.sendUsage(sender, "player");
        } else {
            sender.sendMessage(mm.getMessage("noPermission"));
        }
    }

    @Override
    public String name() {
        return plugin.cmdRoot.help;
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
