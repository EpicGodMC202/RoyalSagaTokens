package me.epicgodmc.royalsagatokens;

import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import me.epicgodmc.royalsagatokens.utilities.MsgFormatter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenTop {

    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;

    private ArrayList<String> topTen = new ArrayList<>();
    private Map<UUID, Integer> TopTenCache = new HashMap<>();


    private static TokenTop instance;

    public static TokenTop getInstance() {
        return instance;
    }


    public TokenTop() {
        instance = this;
    }

    public void sendTopTen(CommandSender player) {
        for (String str : topTen) {
            player.sendMessage(mm.applyCC(str));
        }
    }

    public void CalculateTopTen() {
        TopTenCache.clear();

        for (UUID uuid : TokenPlayers.getInstance().getCache().keySet()) {
            TopTenCache.put(uuid, TokenPlayers.getInstance().getByUUID(uuid).getTokens());
        }

        String header = plugin.files.getLangConfig().getString("topTen.header");
        String footer = plugin.files.getLangConfig().getString("topTen.footer");
        String format = plugin.files.getLangConfig().getString("topTen.format");
        AtomicInteger index = new AtomicInteger(1);
        topTen.add(mm.applyCC(header));
        TopTenCache.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).forEach(e -> {
            String formatted = new MsgFormatter(format.replace("%count%", String.valueOf(index.getAndIncrement()))).formatPlayer(Bukkit.getOfflinePlayer(e.getKey()).getName()).formatAmount(e.getValue()).toString();
            topTen.add(mm.applyCC(formatted));
        });
        topTen.add(mm.applyCC(footer));
        TopTenCache.clear();
    }

}
