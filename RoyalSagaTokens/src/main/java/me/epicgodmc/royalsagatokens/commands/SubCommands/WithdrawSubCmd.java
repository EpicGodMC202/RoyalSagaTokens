package me.epicgodmc.royalsagatokens.commands.SubCommands;

import me.epicgodmc.royalsagatokens.Objects.SubCommand;
import me.epicgodmc.royalsagatokens.Objects.TokenPlayer;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.TokenPlayers;
import me.epicgodmc.royalsagatokens.utilities.ItemBuilder;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import me.epicgodmc.royalsagatokens.utilities.MsgFormatter;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WithdrawSubCmd extends SubCommand {

    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private FileConfiguration config = plugin.config;
    private MessageManager mm = plugin.mm;

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                TokenPlayer tPlayer = TokenPlayers.getInstance().getByUUID(player.getUniqueId());
                if (player.hasPermission("tokens.access")) {
                    int amt = plugin.util.parseInt(args[0]);
                    if (amt != -1) {
                        if (tPlayer.hasFunds(amt)) {
                            if (amt >= 1) {
                                if (!plugin.util.invIsFull(player.getInventory())) {
                                    Material materialValue = Material.valueOf(config.getString("items.withdraw.item").toUpperCase());
                                    int itemDamage = config.getInt("items.withdraw.damage");
                                    boolean glow = config.getBoolean("items.withdraw.glow");
                                    String displayName = config.getString("items.withdraw.name");
                                    List<String> lore = new ArrayList<>();
                                    config.getStringList("items.withdraw.lore").forEach((e) -> {

                                        String text = new MsgFormatter(e).formatAmount(amt).formatPlayer(player.getName()).toString();

                                        lore.add(mm.applyCC(text));
                                    });


                                    ItemStack item = new ItemBuilder(materialValue, 1, (byte) itemDamage).setGlow(glow).setName(mm.applyCC(displayName)).setLore(lore).addNbtInt("Tokens", amt).toItemStack();
                                    player.getInventory().addItem(item);
                                    tPlayer.removeTokens(amt);

                                    String msg = new MsgFormatter(mm.getMessage("withdrawTokens")).formatAmount(amt).toString();
                                    player.sendMessage(msg);

                                } else {
                                    String msg = new MsgFormatter(mm.getMessage("fullInventory")).formatPlayer(player.getName()).toString();
                                    player.sendMessage(mm.applyCC(msg));
                                }
                            } else {
                                player.sendMessage(mm.getMessage("minimalWithdrawBlocked"));
                            }
                        } else {
                            player.sendMessage(mm.getMessage("notEnoughTokens"));
                        }
                    } else {
                        player.sendMessage(mm.getMessage("introduceInt"));
                    }
                } else {
                    player.sendMessage(mm.getMessage("noPermission"));
                }
            } else {
                sender.sendMessage(mm.getMessage("invalidArgLenght").replace("%count%", "1"));
            }
        } else {
            sender.sendMessage(mm.getMessage("onlyPlayers"));
        }
    }

    @Override
    public String name() {
        return plugin.cmdRoot.withdraw;
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
