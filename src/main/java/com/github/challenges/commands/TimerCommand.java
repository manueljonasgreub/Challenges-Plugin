package com.github.challenges.commands;

import com.github.challenges.Challenges;
import com.github.challenges.challenge.Challenge;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TimerCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        Challenge challenge = Challenges.getInstance().getChallenge();

        if (!(commandSender instanceof Player)) {
            return false;
        }

        if (args.length < 1) {
            return false;
        }


        switch (args[0]) {

            case "start":
                challenge.start();
                return true;

            case "resume":
                if(!Challenges.getInstance().getChallenge().isRunning()) challenge.start();
                return true;

            case "pause":
                challenge.pause();
                return true;

            case "set":
                if (args.length < 2) {
                    return false;
                }
                try {
                    challenge.set(Integer.parseInt(args[1]));
                } catch (Exception ex) {
                    Challenges.getInstance().getLogger().warning(ex.getMessage());
                }
                return true;

            default:
                return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (strings.length == 1) {
            return List.of("start", "resume", "pause", "set");
        }

        if (strings.length == 2 && strings[0].equals("set")) {
            return List.of("<time>");
        }

        return List.of("");
    }
}
