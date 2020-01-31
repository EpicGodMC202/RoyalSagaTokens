package me.epicgodmc.royalsagatokens.Listeners;

import me.epicgodmc.royalsagatokens.Objects.TokenPlayer;
import me.epicgodmc.royalsagatokens.TokenPlayers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ShopClose implements Listener
{

    @EventHandler
    public void invClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        TokenPlayer tokenPlayer = TokenPlayers.getInstance().getByUUID(player.getUniqueId());
        if (tokenPlayer == null) return;
        if (tokenPlayer.isInShop())
        {
            tokenPlayer.setCurrentShop("null");
        }
    }

}
