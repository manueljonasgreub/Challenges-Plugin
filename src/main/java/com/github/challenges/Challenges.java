package com.github.challenges;

import com.github.challenges.challenge.Challenge;
import com.github.challenges.commands.HealCommand;
import com.github.challenges.commands.ResetCommand;
import com.github.challenges.commands.SettingsCommand;
import com.github.challenges.commands.TimerCommand;
import com.github.challenges.gui.GUIManager;
import com.github.challenges.listeners.PlayerDamageListener;
import com.github.challenges.listeners.PlayerDamageListener;
import com.github.challenges.listeners.PlayerDeathListener;
import com.github.challenges.listeners.QuizAnswerListener;
import com.github.challenges.listeners.InputBlockListener;
import com.github.challenges.listeners.InputBlockListener;
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
        setDefaultConfig();

        if (getConfig().getBoolean("isReset")) {
            DirectoryManager.resetWorldDirectory();
            getConfig().set("isReset", false);
            saveConfig();
        }


    }

    @Override
    public void onEnable() {
        challenge = new Challenge();
        challenge.setAllDieOnDeath(getConfig().getBoolean("isAllDieOnDeath"));

        BatchFileCreator.createBatchFileIfNotExists();

        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("heal").setExecutor(new HealCommand());

        // Listener registrieren
        getServer().getPluginManager().registerEvents(
                new QuizAnswerListener(challenge.getCurrentQuestions()),
                this
        );
        getServer().getPluginManager().registerEvents(
                new InputBlockListener(challenge.getCurrentQuestions()),
                this
        );
        getServer().getPluginManager().registerEvents(new GUIManager(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);

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

    private void setDefaultConfig() {
        if (!getConfig().contains("isReset")) {
            getConfig().set("isReset", false);
            saveConfig();
        }
        if (!getConfig().contains("isAllDieOnDeath")) {
            getConfig().set("isAllDieOnDeath", true);
            saveConfig();
        }
        if (!getConfig().contains("isUltraHardcore")) {
            getConfig().set("isUltraHardcore", false);
            saveConfig();
        }
        if (!getConfig().contains("isPVP")) {
            getConfig().set("isPVP", true);
            saveConfig();
        }
        if (!getConfig().contains("isSplitHearts")) {
            getConfig().set("isSplitHearts", false);
            saveConfig();
        }
        if (!getConfig().contains("isQuestionsActive")) {
            getConfig().set("isQuestionsActive", false);
            saveConfig();
        }
        if (!getConfig().contains("isSneakForbidden")) {
            getConfig().set("isSneakForbidden", false);
            saveConfig();
        }
        if (!getConfig().contains("isJumpForbidden")) {
            getConfig().set("isJumpForbidden", false);
            saveConfig();
        }
    }

    public static Challenges getInstance() {
        return getPlugin(Challenges.class);
    }

}
