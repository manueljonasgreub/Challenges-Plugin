package com.github.challenges.commands;

import com.github.challenges.ChallengesMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        if (!(commandSender instanceof Player)) {
            return false;
        }

        if (args.length != 1) {
            return false;
        }

        if (args[0].equals("confirm")) {

            for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
                onlinePlayer.kickPlayer("§cServer reset");
            }

            ChallengesMain.getInstance().getConfig().set("isReset", true);
            ChallengesMain.getInstance().saveConfig();
            ChallengesMain.getInstance().restartServer();
        }


        return false;
    }
}
