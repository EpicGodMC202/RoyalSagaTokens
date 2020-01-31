package me.epicgodmc.royalsagatokens.commands;

import me.epicgodmc.royalsagatokens.Objects.SubCommand;
import me.epicgodmc.royalsagatokens.RoyalSagaTokens;
import me.epicgodmc.royalsagatokens.TokenPlayers;
import me.epicgodmc.royalsagatokens.commands.SubCommands.*;
import me.epicgodmc.royalsagatokens.utilities.MessageManager;
import me.epicgodmc.royalsagatokens.utilities.MsgFormatter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CmdRoot implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<>();
    private RoyalSagaTokens plugin = RoyalSagaTokens.getInstance();
    private MessageManager mm = plugin.mm;


    public CmdRoot() {

    }


    public String main = "tokens";
    //SubCmds
    public String shop = "shop";
    public String give = "give";
    public String take = "take";
    public String withdraw = "withdraw";
    public String help = "help";
    public String giveAll = "giveall";
    public String shoplist = "shoplist";
    public String pay = "pay";
    public String top = "top";
    public String clear = "clear";
    public String reload = "reload";
    //

    public void setup() {
        plugin.getCommand(main).setExecutor(this);

        this.commands.add(new ShopSubCmd());
        this.commands.add(new GiveSubCmd());
        this.commands.add(new TakeSubCmd());
        this.commands.add(new WithdrawSubCmd());
        this.commands.add(new HelpSubCmd());
        this.commands.add(new GiveAllSubCmd());
        this.commands.add(new ShopListSubCmd());
        this.commands.add(new PaySubCmd());
        this.commands.add(new TopTenSubCmd());
        this.commands.add(new ClearSubCmd());
        this.commands.add(new ReloadSubCmd());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase(main)) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    String msg = new MsgFormatter(mm.getMessage("showTokens")).formatBalance(TokenPlayers.getInstance().getByUUID(((Player) sender).getUniqueId()).getTokens()).toString();
                    sender.sendMessage(mm.applyCC(msg));
                    return true;
                } else {
                    sender.sendMessage(mm.getMessage("onlyPlayers"));
                    return true;
                }
            }

            SubCommand target = this.get(args[0]);

            if (target == null) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage(mm.getMessage("invalidArgs"));
                    return true;
                } else {
                    if (player.hasPermission("tokens.access")) {
                        String msg = new MsgFormatter(mm.getMessage("showTokensOther")).formatPlayer(player.getName()).formatBalance(TokenPlayers.getInstance().getByUUID(player.getUniqueId()).getTokens()).toString();
                        sender.sendMessage(mm.applyCC(msg));
                        return true;
                    } else {
                        player.sendMessage(mm.getMessage("noPermission"));
                        return true;
                    }
                }


            }

            ArrayList<String> argList = new ArrayList<>();
            argList.addAll(Arrays.asList(args));
            argList.remove(0);

            String[] arguments = argList.toArray(new String[argList.size()]);

            try {
                target.onCommand(sender, arguments);
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(mm.getMessage("commandError"));
                return true;
            }
        }
        return true;
    }


    private SubCommand get(String name) {
        Iterator<SubCommand> subcommands = this.commands.iterator();

        while (subcommands.hasNext()) {
            SubCommand sCmd = (SubCommand) subcommands.next();

            if (sCmd.name().equalsIgnoreCase(name)) {
                return sCmd;
            }

            String[] aliases;
            int length = (aliases = sCmd.aliases()).length;

            for (int var5 = 0; var5 < length; ++var5) {
                String alias = aliases[var5];
                if (name.equalsIgnoreCase(alias)) {
                    return sCmd;
                }
            }
        }
        return null;
    }

}
