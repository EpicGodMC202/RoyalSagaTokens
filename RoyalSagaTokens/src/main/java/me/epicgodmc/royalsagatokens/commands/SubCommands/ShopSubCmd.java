package me.epicgodmc.royalsagatokens.commands.SubCommands;

import me.epicgodmc.royalsagatokens.Objects.SubCommand;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopSubCmd extends SubCommand {

    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                plugin.shopManager.openMainShop(player);
            } else if (args.length == 1) {
                if (plugin.shopManager.shopExists(player, args[0])) {
                    plugin.shopManager.attemptShop(player, args[0]);
                } else {
                    return;
                }
            } else {
                player.sendMessage(mm.getMessage("invalidArgLenght").replace("%count%", "1"));
            }
        } else {
            sender.sendMessage(mm.getMessage("onlyPlayers"));
        }
    }

    @Override
    public String name() {
        return plugin.cmdRoot.shop;
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
