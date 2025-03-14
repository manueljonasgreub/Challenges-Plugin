package com.github.challenges;

import com.github.challenges.commands.ResetCommand;
import com.github.challenges.utils.BatchFileCreator;
import com.github.challenges.utils.DirectoryManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Challenges extends JavaPlugin {

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

        BatchFileCreator.createBatchFileIfNotExists();

        getCommand("reset").setExecutor(new ResetCommand());

    }

    @Override
    public void onDisable() {
        saveConfig();
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
