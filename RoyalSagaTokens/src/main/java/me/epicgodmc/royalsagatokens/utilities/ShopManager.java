package me.epicgodmc.royalsagatokens.utilities;

import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.TokenPlayers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopManager {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;
    private FileConfiguration config = plugin.getConfig();
    private FileConfiguration shops = plugin.files.getShopConfig();


    public boolean shopExists(Player player, String identifier) {
        if (shops == null) Bukkit.broadcastMessage("1");

        if (shops.contains(identifier) && config.contains("shop.shops." + identifier)) {
            return true;
        } else {
            player.sendMessage(mm.getMessage("shopNotFound"));
            return false;
        }

    }

    public void attemptShop(Player player, String identifier) {
        if (player.hasPermission("tokens.access." + identifier)) {
            int invSize = shops.getInt(identifier + ".size");
            String invName = shops.getString(identifier + ".inventoryName");
            Inventory inv = Bukkit.createInventory(null, invSize, mm.applyCC(invName));

            for (String decoIdentifier : shops.getConfigurationSection(identifier + ".panels").getKeys(false)) {
                ItemStack stack = plugin.util.loadItem(shops, identifier + ".panels." + decoIdentifier).toItemStack();

                for (String str : shops.getStringList(identifier + ".panels." + decoIdentifier + ".slots")) {
                    int slot = plugin.util.parseInt(str);
                    if (slot != -1) {

                        inv.setItem(slot, stack);

                    } else {
                        if (config.getBoolean("doAdminMessageErrors"))
                            mm.sendAdminMessage("An error has been made at SLOTS for SHOP:" + identifier + " , ITEM:" + decoIdentifier);
                        return;
                    }
                }
            }

            for (String itemIdentifier : shops.getConfigurationSection(identifier + ".items").getKeys(false)) {
                ItemStack item = plugin.util.loadItem(shops, identifier + ".items." + itemIdentifier).addNbtString("ShopItem", itemIdentifier).toItemStack();
                String currentPath = identifier + ".items." + itemIdentifier;
                inv.setItem(shops.getInt(currentPath + ".slot"), item);
            }
            inv.setItem(config.getInt("shop.shops." + identifier + ".backButton.slot"), getBackButton(identifier));


            player.openInventory(inv);
            TokenPlayers.getInstance().getByUUID(player.getUniqueId()).setCurrentShop(identifier);

        } else {
            player.sendMessage(mm.getMessage("noPermissionShop"));
        }
    }

    public void openConfirmPage(Player player, String shopIdentifier, String itemIdentifier) {
        int invSize = config.getInt("confirmer.size");
        String invname = config.getString("confirmer.name");

        Inventory confirmerInv = Bukkit.createInventory(null, invSize, mm.applyCC(invname));

        for (String decoIdentifier : config.getConfigurationSection("confirmer.panels").getKeys(false)) {
            for (String slot : config.getStringList("confirmer.panels." + decoIdentifier + ".slots")) {
                confirmerInv.setItem(Integer.parseInt(slot), plugin.util.loadItem(config, "confirmer.panels." + decoIdentifier).toItemStack());
            }
        }
        confirmerInv.setItem(config.getInt("confirmer.accept.slot"), getAcceptButton(shops.getInt(shopIdentifier + ".items." + itemIdentifier + ".price"), shopIdentifier, itemIdentifier));
        confirmerInv.setItem(config.getInt("confirmer.decline.slot"), getDeclineButton(shopIdentifier));

        player.openInventory(confirmerInv);
    }

    private ItemStack getAcceptButton(int cost, String shopIdentifier, String itemIdentifier) {
        return plugin.util.loadItem(config, "confirmer.accept").addNbtString("Confirmer", "accept").addNbtInt("itemCost", cost).addNbtString("shop", shopIdentifier).addNbtString("item", itemIdentifier).toItemStack();
    }

    private ItemStack getDeclineButton(String shopIdentifier) {
        return plugin.util.loadItem(config, "confirmer.decline").addNbtString("Confirmer", "decline").addNbtString("shop", shopIdentifier).toItemStack();
    }

    public void openMainShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, config.getInt("shop.size"), mm.applyCC(config.getString("shop.name")));


        for (String shopName : config.getConfigurationSection("shop.shops").getKeys(false)) {

            ItemStack item = plugin.util.loadItem(config, "shop.shops." + shopName + ".icon").addNbtString("ShopOption", shopName).toItemStack();
            int slot = config.getInt("shop.shops." + shopName + ".icon.slot");
            inv.setItem(slot, item);
        }

        Material iconMat = Material.valueOf(config.getString("shop.currentTokens.item").toUpperCase());
        String name = mm.applyCC(config.getString("shop.currentTokens.name"));
        int itemAmt = config.getInt("shop.currentTokens.itemAmount");
        int itemDamage = config.getInt("shop.currentTokens.damage");
        List<String> lore = new ArrayList<>();
        config.getStringList("shop.currentTokens.lore").forEach((e) -> {
            lore.add(mm.applyCC(e.replace("%balance%", String.valueOf(TokenPlayers.getInstance().getByUUID(player.getUniqueId()).getTokens()))));
        });

        ItemStack item = new ItemBuilder(iconMat, itemAmt, (byte) itemDamage).setName(name).setLore(lore).toItemStack();

        inv.setItem(config.getInt("shop.currentTokens.slot"), item);


        player.openInventory(inv);
        TokenPlayers.getInstance().getByUUID(player.getUniqueId()).setCurrentShop("main");
    }

    public ItemStack getBackButton(String shopIdentifier) {
        return plugin.util.loadItem(config, "shop.shops." + shopIdentifier + ".backButton").addNbtString("backButton", shopIdentifier).toItemStack();
    }


}
