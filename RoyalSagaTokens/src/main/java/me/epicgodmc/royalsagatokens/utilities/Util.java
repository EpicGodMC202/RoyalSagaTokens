package me.epicgodmc.royalsagatokens.utilities;


import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Util {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;
    private FileConfiguration config = plugin.config;

    public boolean invIsFull(Inventory inv) {
        for (ItemStack item : inv.getContents()) {
            if (item == null || item.getType().equals(Material.AIR)) return false;
        }
        return true;

    }

    public void decrementItemInHandBy(int amt, Player player) {
        ItemStack item = player.getItemInHand();
        int current = item.getAmount();

        if (current == 1) {
            player.setItemInHand(new ItemStack(Material.AIR));
            return;
        }
        item.setAmount(current - amt);
        player.setItemInHand(item);

    }


    public ItemBuilder loadItem(FileConfiguration c, String itemDataPath) {
        boolean errors = this.config.getBoolean("doAdminMessageErrors");

        boolean glow = false;
        Material mat = null;
        String name = "";
        List<String> lore = new ArrayList<>();
        int damage = 0;
        int itemAmount = 0;

        try {
            mat = Material.valueOf(c.getString(itemDataPath + ".item").toUpperCase());
            name = c.getString(itemDataPath + ".name");
            itemAmount = c.getInt(itemDataPath + ".itemAmount");
            damage = c.getInt(itemDataPath + ".damage");
            c.getStringList(itemDataPath + ".lore").forEach((e) -> {
                lore.add(mm.applyCC(e));
            });
            if (c.isSet(itemDataPath + ".glow")) {
                glow = c.getBoolean(itemDataPath + ".glow");
            }
        } catch (NullPointerException e) {
            if (errors) mm.sendAdminMessage("Something went wrong when loading item in workpath: " + itemDataPath);

            return null;
        }

        return new ItemBuilder(mat, itemAmount, (byte) damage).setName(mm.applyCC(name)).setLore(lore).setGlow(glow);
    }


    public int parseInt(String number) {
        int output = 0;
        try {
            output = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return -1;
        }
        return output;
    }

}
