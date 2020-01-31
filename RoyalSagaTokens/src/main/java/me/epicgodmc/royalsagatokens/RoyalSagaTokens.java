package me.epicgodmc.royalsagatokens;

import me.epicgodmc.royalsagatokens.Listeners.*;
import me.epicgodmc.royalsagatokens.Objects.TokenPlayer;
import me.epicgodmc.royalsagatokens.Threads.RenewTopTen;
import me.epicgodmc.royalsagatokens.commands.CmdRoot;
import me.epicgodmc.royalsagatokens.utilities.Files;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import me.epicgodmc.royalsagatokens.utilities.ShopManager;
import me.epicgodmc.royalsagatokens.utilities.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class RoyalSagaTokens extends JavaPlugin {
    private static RoyalSagaTokens instance;

    public static RoyalSagaTokens getInstance() {
        return instance;
    }

    public FileConfiguration config = this.getConfig();
    public PluginManager pm = this.getServer().getPluginManager();


    public MessageManager mm;
    public Files files;
    public CmdRoot cmdRoot;
    public Util util;
    public ShopManager shopManager;


    @Override
    public void onEnable() {
        instance = this;

        files = new Files();
        files.createFiles();
        saveDefaultConfig();
        loadInstances();
        loadEvents();
        cmdRoot.setup();

        loadData();
        new RenewTopTen(this);
    }

    @Override
    public void onDisable() {
        saveData();
        instance = null;

    }

    public void loadInstances() {
        mm = new MessageManager();
        cmdRoot = new CmdRoot();
        util = new Util();
        shopManager = new ShopManager();
        new TokenTop();

    }

    public void loadEvents() {
        pm.registerEvents(new TokenPlayers(), this);
        pm.registerEvents(new Redeem(), this);
        pm.registerEvents(new ShopChooseOption(), this);
        pm.registerEvents(new ShopClose(), this);
        pm.registerEvents(new ShopPurchaseItems(), this);
        pm.registerEvents(new ShopPurchaseConfirm(), this);
        if (config.getBoolean("drop.enabled")) {
            pm.registerEvents(new Farming(), this);
        }

    }


    public void loadData() {
        FileConfiguration data = files.getPlayerDataConfig();
        if (!data.isSet("data") || data.getConfigurationSection("data").getKeys(false).isEmpty()) return;
        for (String uuidStr : data.getConfigurationSection("data").getKeys(false)) {
            UUID uuid = UUID.fromString(uuidStr);
            int balance = data.getInt("data." + uuidStr + ".tokens");

            TokenPlayers.getInstance().loadPlayer(uuid, new TokenPlayer(uuid, balance));
        }

    }

    public void saveData() {
        if (TokenPlayers.getInstance().getCache().isEmpty()) return;
        FileConfiguration data = files.getPlayerDataConfig();
        for (UUID uuid : TokenPlayers.getInstance().getCache().keySet()) {
            TokenPlayer current = TokenPlayers.getInstance().getByUUID(uuid);
            data.set("data." + uuid.toString() + ".tokens", current.getTokens());
        }
        files.savePlayerDataConfig();


    }
}
