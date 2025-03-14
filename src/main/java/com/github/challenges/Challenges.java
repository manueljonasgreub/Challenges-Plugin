package com.github.challenges;

import com.github.challenges.challenge.Challenge;
import com.github.challenges.commands.ResetCommand;
import com.github.challenges.commands.TimerCommand;
import com.github.challenges.utils.BatchFileCreator;
import com.github.challenges.utils.DirectoryManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Challenges extends JavaPlugin {

    Challenge challenge;

    @Override
    public void onLoad() {

        saveConfig();

        if (!getConfig().contains("isReset")) {
            getConfig().set("isReset", false);
            saveConfig();
        }

        if (getConfig().getBoolean("isReset")) {

            DirectoryManager.resetWorldDirectory();
            getConfig().set("isReset", false);
            saveConfig();
        }
    }

    @Override
    public void onEnable() {

        challenge = new Challenge();
        BatchFileCreator.createBatchFileIfNotExists();

        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("timer").setExecutor(new TimerCommand());

    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void restartServer() {
        try {
            Runtime.getRuntime().exec("cmd /c start restart.bat");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.shutdown();
    }

    public static Challenges getInstance() {
        return getPlugin(Challenges.class);
    }

}
