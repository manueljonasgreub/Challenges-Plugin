package com.github.challenges.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HealCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (strings.length == 0) {
            for (Player player : commandSender.getServer().getOnlinePlayers()) {
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setSaturation(5);
            }
            return true;
        }

        if (strings.length == 1) {
            try {
                String name = strings[0];
                for (Player player : commandSender.getServer().getOnlinePlayers()) {
                    if (player.getName().equalsIgnoreCase(name)) {
                        player.setHealth(20);
                        player.setFoodLevel(20);
                        player.setSaturation(5);
                        return true;
                    }
                }

            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (strings.length == 1) {
            return commandSender.getServer().getOnlinePlayers().stream().map(Player::getName).toList();
        }

        return List.of("");
    }

}
