package me.epicgodmc.royalsagatokens.commands.SubCommands;

import me.epicgodmc.royalsagatokens.Objects.SubCommand;
import me.epicgodmc.royalsagatokens.Objects.TokenPlayer;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.TokenPlayers;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import me.epicgodmc.royalsagatokens.utilities.MsgFormatter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveAllSubCmd extends SubCommand {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender.hasPermission("tokens.admin")) {
            if (args.length == 1) {
                int amt = plugin.util.parseInt(args[0]);
                if (amt != -1) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        TokenPlayer tp = TokenPlayers.getInstance().getByUUID(player.getUniqueId());
                        tp.addTokens(amt);
                    }
                    String senderMsg = new MsgFormatter(mm.getMessage("giveTokensAll")).formatAmount(amt).toString();
                    String receiverMsg = new MsgFormatter(mm.getMessage("giveTokensAllBroadcast")).formatPlayer(sender.getName()).formatAmount(amt).toString();

                    sender.sendMessage(mm.applyCC(senderMsg));
                    Bukkit.broadcastMessage(mm.applyCC(receiverMsg));


                } else {
                    sender.sendMessage(mm.getMessage("introduceInt"));
                }
            } else {
                sender.sendMessage(mm.getMessage("invalidArgLenght").replace("%count%", "1"));
            }

        } else {
            sender.sendMessage(mm.getMessage("noPermission"));
        }
    }

    @Override
    public String name() {
        return plugin.cmdRoot.giveAll;
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"addall"};
    }
}
