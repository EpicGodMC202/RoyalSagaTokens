package me.epicgodmc.royalsagatokens.Listeners;

import me.epicgodmc.royalsagatokens.Objects.TokenPlayer;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.TokenPlayers;
import me.epicgodmc.royalsagatokens.utilities.MsgFormatter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

import java.util.concurrent.ThreadLocalRandom;


public class Farming implements Listener {
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private int maxRange = plugin.config.getInt("drop.maxRange");
    private int chance = plugin.config.getInt("drop.chance");
    private int tokenAmt = plugin.config.getInt("drop.amount");


    @EventHandler
    public void onFarm(BlockDamageEvent e) {
        Player player = e.getPlayer();
        Block b = e.getBlock();
        if (isApplicable(b)) {
            if (doChance(chance, maxRange)) {
                TokenPlayer tokenPlayer = TokenPlayers.getInstance().getByUUID(player.getUniqueId());
                tokenPlayer.addTokens(tokenAmt);
                String msg = new MsgFormatter(plugin.mm.getMessage("receiveTokensFromFarming")).formatAmount(tokenAmt).toString();
                player.sendMessage(plugin.mm.applyCC(msg));

            }
        }

    }
    private boolean isApplicable(Block block) {
        BlockState blockState = block.getState();
        switch (blockState.getType()) {
            case CARROT:
            case POTATO:
                return blockState.getRawData() == CropState.RIPE.getData();

            case CROPS:
                return ((Crops) blockState.getData()).getState() == CropState.RIPE;

            case NETHER_WARTS:
                return ((NetherWarts) blockState.getData()).getState() == NetherWartsState.RIPE;

            case COCOA:
                return ((CocoaPlant) blockState.getData()).getSize() == CocoaPlant.CocoaPlantSize.LARGE;
            default:
                return false;
        }
    }

    private boolean doChance(int chance, int maxRange) {
        int random = ThreadLocalRandom.current().nextInt(maxRange);
        if (random <= chance) {
            return true;
        }
        return false;

    }


}
