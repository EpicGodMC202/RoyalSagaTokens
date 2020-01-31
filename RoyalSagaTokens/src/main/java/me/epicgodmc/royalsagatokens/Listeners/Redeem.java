package me.epicgodmc.royalsagatokens.Listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.epicgodmc.royalsagatokens.Objects.TokenPlayer;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.TokenPlayers;
import me.epicgodmc.royalsagatokens.utilities.MsgFormatter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Redeem implements Listener {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null) return;
        if (item.getType().equals(Material.AIR)) return;
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.hasNBTData() && nbtItem.hasKey("Tokens")) {
            int value = nbtItem.getInteger("Tokens");
            plugin.util.decrementItemInHandBy(1, player);
            TokenPlayer tokenPlayer = TokenPlayers.getInstance().getByUUID(player.getUniqueId());
            tokenPlayer.addTokens(value);

            String msg = new MsgFormatter(plugin.mm.getMessage("depositTokens")).formatAmount(value).toString();
            player.sendMessage(plugin.mm.applyCC(msg));
        }
    }
}

