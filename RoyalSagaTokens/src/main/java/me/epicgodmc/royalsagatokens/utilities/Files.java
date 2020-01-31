package me.epicgodmc.royalsagatokens.utilities;

import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Files {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();


    //Files
    private File playerDataFile;
    private FileConfiguration playerDataConfig;

    private File langFile;
    private FileConfiguration langConfig;

    private File shopFile;
    private FileConfiguration shopConfig;
//


    public void createFiles() {
        shopFile = new File(plugin.getDataFolder(), "Shops.yml");
        langFile = new File(plugin.getDataFolder(), "Lang.yml");
        playerDataFile = new File(plugin.getDataFolder(), "PlayerData.yml");

        if (!shopFile.exists()) {
            shopFile.getParentFile().mkdirs();
            plugin.saveResource("Shops.yml", false);
        }
        if (!playerDataFile.exists()) {
            playerDataFile.getParentFile().mkdirs();
            plugin.saveResource("PlayerData.yml", false);
        }
        if (!langFile.exists()) {
            langFile.getParentFile().mkdirs();
            plugin.saveResource("Lang.yml", false);
        }

        shopConfig = new YamlConfiguration();
        langConfig = new YamlConfiguration();
        playerDataConfig = new YamlConfiguration();

        try {
            shopConfig.load(shopFile);
            playerDataConfig.load(playerDataFile);
            langConfig.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getShopConfig() {
        return this.shopConfig;
    }

    public void reloadShopConfig() {
        if (shopFile == null) {
            shopFile = new File(plugin.getDataFolder(), "Shops.yml");
        }
        shopConfig = YamlConfiguration.loadConfiguration(shopFile);

        InputStream defConfigSteam = plugin.getResource("Shops.yml");
        if (defConfigSteam != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigSteam);
            shopConfig.setDefaults(defConfig);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[RoyalSagaTokens] Reloaded Shops.yml Configuration");
    }

    public void saveShopConfig() {
        try {
            this.shopConfig.save(this.shopFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getPlayerDataConfig() {
        return this.playerDataConfig;
    }

    public void reloadPlayerDataConfig() {
        if (playerDataFile == null) {
            playerDataFile = new File(plugin.getDataFolder(), "PlayerData.yml");
        }
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);

        InputStream defConfigSteam = plugin.getResource("PlayerData.yml");
        if (defConfigSteam != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigSteam);
            playerDataConfig.setDefaults(defConfig);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[RoyalSagaTokens] Reloaded PlayerData.yml Configuration");
    }

    public void savePlayerDataConfig() {
        try {
            this.playerDataConfig.save(this.playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getLangConfig() {
        return this.langConfig;
    }

    public void reloadLangConfig() {
        if (langFile == null) {
            langFile = new File(plugin.getDataFolder(), "Lang.yml");
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);

        InputStream defConfigSteam = plugin.getResource("Lang.yml");
        if (defConfigSteam != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigSteam);
            langConfig.setDefaults(defConfig);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[RoyalSagaTokens] Reloaded Lang.yml Configuration");
    }

    public void saveLangConfig() {
        try {

            langConfig.save(langFile);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void reloadConfigs() {
        reloadShopConfig();
        reloadPlayerDataConfig();
        reloadLangConfig();

        plugin.reloadConfig();
        plugin.saveConfig();

        plugin.pm.disablePlugin(plugin);
        plugin.pm.enablePlugin(plugin);

        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[RoyalSagaTokens] Reloaded Plugin");
    }


}
