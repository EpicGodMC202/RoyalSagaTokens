package me.epicgodmc.royalsagatokens.Listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.epicgodmc.royalsagatokens.Objects.TokenPlayer;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.TokenPlayers;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import me.epicgodmc.royalsagatokens.utilities.MsgFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopPurchaseConfirm implements Listener {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private FileConfiguration config = plugin.config;
    private FileConfiguration shops = plugin.files.getShopConfig();
    private MessageManager mm = plugin.mm;

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equalsIgnoreCase(mm.applyCC(config.getString("confirmer.name")))) {
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            if (clicked == null || clicked.getType().equals(Material.AIR)) return;

            NBTItem nbtItem = new NBTItem(clicked);
            if (nbtItem.hasKey("Confirmer")) {
                Player player = (Player) e.getWhoClicked();
                TokenPlayer tokenPlayer = TokenPlayers.getInstance().getByUUID(player.getUniqueId());

                switch (nbtItem.getString("Confirmer")) {
                    case "accept":
                        int cost = nbtItem.getInteger("itemCost");
                        String shop = nbtItem.getString("shop");
                        String item = nbtItem.getString("item");
                        tokenPlayer.removeTokens(cost);
                        runCmds(player, shop, item);
                        plugin.shopManager.attemptShop(player, shop);

                        String msg = new MsgFormatter(mm.getMessage("purchaseSuccess")).formatItem(mm.applyCC(shops.getString(shop + ".items." + item + ".name"))).formatCost(cost).formatAmount(tokenPlayer.getTokens()).toString();
                        player.sendMessage(mm.applyCC(msg));

                        break;
                    case "decline":
                        String shop2 = nbtItem.getString("shop");
                        plugin.shopManager.attemptShop(player, shop2);
                        break;
                    default:
                        break;

                }
            }
        }
    }

    public void runCmds(Player player, String shopIdentifier, String itemIdentifier) {
        for (String str : shops.getStringList(shopIdentifier + ".items." + itemIdentifier + ".commands")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), str.replace("%player%", player.getName()));
        }

    }


}
