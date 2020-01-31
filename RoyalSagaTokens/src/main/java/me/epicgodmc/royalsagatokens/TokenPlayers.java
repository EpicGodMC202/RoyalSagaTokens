package me.epicgodmc.royalsagatokens;

import me.epicgodmc.royalsagatokens.Objects.TokenPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.HashMap;
import java.util.UUID;

public class TokenPlayers implements Listener {

    private static TokenPlayers instance;

    public static TokenPlayers getInstance() {
        return instance;
    }

    public TokenPlayers() {
        instance = this;
    }

    private HashMap<UUID, TokenPlayer> cache = new HashMap<>();

    public TokenPlayer getByUUID(UUID uuid) {
        return cache.get(uuid);
    }

    public boolean isLoaded(UUID uuid) {
        return cache.containsKey(uuid);
    }

    public void loadPlayer(UUID uuid, TokenPlayer tp) {
        cache.putIfAbsent(uuid, tp);
    }

    public HashMap<UUID, TokenPlayer> getCache() {
        return cache;
    }

    @EventHandler
    public void join(AsyncPlayerPreLoginEvent e) {
        cache.putIfAbsent(e.getUniqueId(), new TokenPlayer(e.getUniqueId(), RoyalSagaTokens.getInstance().config.getInt("startAmount")));
    }

}
