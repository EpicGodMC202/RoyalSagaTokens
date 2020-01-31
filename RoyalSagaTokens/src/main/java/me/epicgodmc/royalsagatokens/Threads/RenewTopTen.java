package me.epicgodmc.royalsagatokens.Threads;

import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.TokenTop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class RenewTopTen {

    public RenewTopTen(RoyalSagaTokens plugin) {
        int interval = plugin.config.getInt("tokenTopUpdate") * 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                TokenTop.getInstance().CalculateTopTen();
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Top Ten has been calculated");
            }
        }.runTaskTimer(plugin, 0L, interval);
    }

}
