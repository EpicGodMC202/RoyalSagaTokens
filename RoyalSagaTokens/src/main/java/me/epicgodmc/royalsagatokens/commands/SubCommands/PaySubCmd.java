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

public class PaySubCmd extends SubCommand {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("tokens.access")) {
                if (args.length == 2) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        int amt = plugin.util.parseInt(args[1]);
                        if (amt != -1) {
                            if (amt >= 1) {
                                TokenPlayer tokenSender = TokenPlayers.getInstance().getByUUID(player.getUniqueId());
                                if (tokenSender.hasFunds(amt)) {
                                    TokenPlayer tokenReceiver = TokenPlayers.getInstance().getByUUID(target.getUniqueId());

                                    tokenSender.payTokens(tokenReceiver, amt);

                                    String senderMsg = new MsgFormatter(mm.getMessage("payTokens")).formatAmount(amt).formatPlayer(target.getName()).toString();
                                    String receiverMsg = new MsgFormatter(mm.getMessage("receiveTokensFromOther")).formatAmount(amt).formatPlayer(player.getName()).toString();

                                    player.sendMessage(mm.applyCC(senderMsg));
                                    target.sendMessage(mm.applyCC(receiverMsg));
                                } else {
                                    player.sendMessage(mm.applyCC("notEnoughTokens"));
                                }
                            } else {
                                player.sendMessage(mm.getMessage("minimalPaymentBlocked"));
                            }
                        } else {
                            sender.sendMessage(mm.getMessage("introduceInt"));
                        }
                    } else {
                        String msg = new MsgFormatter(mm.getMessage("playerNotFound")).formatPlayer(args[0]).toString();
                        sender.sendMessage(mm.applyCC(msg));
                    }
                } else {
                    sender.sendMessage(mm.getMessage("invalidArgLenght").replace("%count%", "2"));
                }
            } else {
                sender.sendMessage(mm.getMessage("noPermission"));
            }
        } else {
            sender.sendMessage(mm.getMessage("onlyPlayers"));
        }


    }

    @Override
    public String name() {
        return plugin.cmdRoot.pay;
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
