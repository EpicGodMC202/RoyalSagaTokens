package me.epicgodmc.royalsagatokens.Listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopChooseOption implements Listener {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equalsIgnoreCase(mm.applyCC(plugin.config.getString("shop.name")))) {
            if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)) {
                e.setCancelled(true);
                ItemStack clicked = e.getCurrentItem();
                NBTItem nbtItem = new NBTItem(clicked);
                if (nbtItem.hasKey("ShopOption")) {
                    String shopIdentifier = nbtItem.getString("ShopOption");
                    Player player = (Player) e.getWhoClicked();
                    plugin.shopManager.attemptShop(player, shopIdentifier);
                }
            }
        }
    }

}
