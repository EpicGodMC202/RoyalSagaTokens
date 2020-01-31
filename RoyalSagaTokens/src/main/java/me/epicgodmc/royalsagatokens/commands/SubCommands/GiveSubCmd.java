package me.epicgodmc.royalsagatokens.commands.SubCommands;

import me.epicgodmc.royalsagatokens.Objects.SubCommand;
import me.epicgodmc.royalsagatokens.Objects.TokenPlayer;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.TokenPlayers;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import me.epicgodmc.royalsagatokens.utilities.MsgFormatter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GiveSubCmd extends SubCommand {

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
                        tPlayer.addTokens(amount);

                        String receiverMsg;
                        if (sender instanceof ConsoleCommandSender) {
                            receiverMsg = new MsgFormatter(mm.getMessage("receiveTokensFromConsole")).formatAmount(amount).toString();
                        } else {
                            receiverMsg = new MsgFormatter(mm.getMessage("receiveTokensFromOther")).formatAmount(amount).formatPlayer(sender.getName()).toString();

                        }
                        String senderMsg = new MsgFormatter(mm.getMessage("giveTokens")).formatAmount(amount).formatPlayer(target.getName()).toString();

                        sender.sendMessage(mm.applyCC(senderMsg));
                        target.sendMessage(mm.applyCC(receiverMsg));


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
        return plugin.cmdRoot.give;
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[]{"add"};
    }
}
