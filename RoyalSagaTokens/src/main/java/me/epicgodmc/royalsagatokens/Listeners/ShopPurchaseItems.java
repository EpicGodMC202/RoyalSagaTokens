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

public class ShopPurchaseItems implements Listener {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private FileConfiguration shops = plugin.files.getShopConfig();
    private MessageManager mm = plugin.mm;

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        TokenPlayer tokenPlayer = TokenPlayers.getInstance().getByUUID(player.getUniqueId());
        if (tokenPlayer.isInShop()) {
            e.setCancelled(true);
            String shopIdentifier = tokenPlayer.getCurrentShop();

            ItemStack clicked = e.getCurrentItem();
            if (clicked == null || clicked.getType().equals(Material.AIR)) return;
            NBTItem nbtItem = new NBTItem(clicked);
            if (nbtItem.hasKey("backButton")) {
                plugin.shopManager.openMainShop(player);
                return;
            } else if (nbtItem.hasKey("ShopItem")) {
                String itemIdentifier = nbtItem.getString("ShopItem");
                int cost = shops.getInt(shopIdentifier + ".items." + itemIdentifier + ".price");
                if (tokenPlayer.hasFunds(cost)) {
                    plugin.shopManager.openConfirmPage(player, shopIdentifier, itemIdentifier);
                } else {
                    player.sendMessage(mm.getMessage("notEnoughTokensForPurchase"));
                    return;
                }
                return;
            }
        } else return;
    }
}
