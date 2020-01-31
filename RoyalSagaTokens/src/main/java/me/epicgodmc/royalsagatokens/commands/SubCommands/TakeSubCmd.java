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

public class TakeSubCmd extends SubCommand {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender.hasPermission("tokens.admin")) {
            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    int amount = plugin.util.parseInt(args[1]);
                    if (amount != -1) {
                        TokenPlayer tPlayer = TokenPlayers.getInstance().getByUUID(target.getUniqueId());

                        if (tPlayer.hasFunds(amount)) {
                            tPlayer.removeTokens(amount);
                            String senderMsg = new MsgFormatter(mm.getMessage("takeTokens")).formatAmount(amount).formatPlayer(target.getName()).toString();
                            sender.sendMessage(mm.applyCC(senderMsg));
                        } else {
                            String msg = new MsgFormatter(mm.getMessage("notEnoughTokensOther")).formatPlayer(target.getName()).toString();
                            sender.sendMessage(mm.applyCC(msg));
                            return;
                        }
                    } else {
                        sender.sendMessage(mm.getMessage("introduceInt"));
                    }
                } else {
                    sender.sendMessage(mm.getMessage("playerNotFound"));
                }
            } else {
                sender.sendMessage(mm.getMessage("invalidArgLenght").replace("%count%", String.valueOf(2)));
            }
        } else {
            sender.sendMessage(mm.getMessage("noPermission"));
        }
    }

    @Override
    public String name() {
        return plugin.cmdRoot.take;
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"remove"};
    }
}
