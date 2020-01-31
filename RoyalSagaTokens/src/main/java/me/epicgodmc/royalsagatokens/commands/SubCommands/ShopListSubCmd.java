package me.epicgodmc.royalsagatokens.commands.SubCommands;

import me.epicgodmc.royalsagatokens.Objects.SubCommand;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import me.epicgodmc.royalsagatokens.utilities.MsgFormatter;
import org.bukkit.command.CommandSender;


public class ShopListSubCmd extends SubCommand {

    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;


    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender.hasPermission("tokens.access")) {
            if (args.length == 0) {
                String format = plugin.files.getLangConfig().getString("messages.shopListFormat");

                int index = 1;
                for (String str : plugin.files.getShopConfig().getConfigurationSection("").getKeys(false)) {
                    String msg = new MsgFormatter(format).formatNumber(index).formatName(str).formatDesc(plugin.files.getShopConfig().getString(str + ".description")).toString();
                    sender.sendMessage(mm.applyCC(msg));
                    index++;
                }

            }
        }
    }

    @Override
    public String name() {
        return plugin.cmdRoot.shoplist;
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
