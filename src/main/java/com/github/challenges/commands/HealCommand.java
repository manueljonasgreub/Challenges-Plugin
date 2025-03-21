package com.github.challenges.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        for (Player player : commandSender.getServer().getOnlinePlayers()) {
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setSaturation(5);
        }
        return true;
    }
}
