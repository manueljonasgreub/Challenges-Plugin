package com.github.challenges.challenge;

import com.github.challenges.Challenges;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;

public class Challenge {

    private int time = 0;
    private boolean isRunning = false;
    private boolean isAllDieOnDeath;
    private boolean isUltraHardcore;
    private boolean isPVP;
    private boolean isSplitHearts;

    public Challenge(){

        loadConfig();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Challenges.getInstance(),
                () -> {

                    printTime();

                    if (!isRunning) {
                        return;
                    }

                    time++;

                },
                0,
                20);
    }


    public void start() {
        isRunning = true;
    }

    private void printTime() {

        var players = Challenges.getInstance().getServer().getOnlinePlayers();
        for (Player player : players) {

            Component message = null;

            if (isRunning) {

                int hours = time / 3600;
                int remainingSeconds = time % 3600;
                int minutes = remainingSeconds / 60;
                int seconds = remainingSeconds % 60;

                String secondsFormatted = String.format("%02d", seconds);
                String minutesFormatted = String.format("%02d", minutes);
                String hoursFormatted = String.format("%02d", hours);

                message = Component
                        .text(hoursFormatted + ":" + minutesFormatted + ":" + secondsFormatted)
                        .color(NamedTextColor.GOLD)
                        .decorate(TextDecoration.BOLD);

            } else {

                message = Component
                        .text("Timer pausiert")
                        .color(NamedTextColor.GOLD)
                        .decorate(TextDecoration.BOLD);

            }

            player.sendActionBar(message);

        }

    }

    public void pause() {
        isRunning = false;
    }

    public void set(int seconds) {
        time = seconds;
    }

    public void resume() {
        isRunning = true;
    }

    public void reset() {
        time = 0;
        isRunning = false;
    }

    public boolean isAllDieOnDeath(){;
        return isAllDieOnDeath;
    }

    public void setAllDieOnDeath(boolean isAllDieOnDeath){
        this.isAllDieOnDeath = isAllDieOnDeath;
        Challenges.getInstance().getConfig().set("isAllDieOnDeath", isAllDieOnDeath);
        Challenges.getInstance().saveConfig();
    }

    public boolean isUltraHardcore() {
        return isUltraHardcore;
    }

    public void setUltraHardcore(boolean isUltraHardcore) {
        this.isUltraHardcore = isUltraHardcore;

        Challenges.getInstance().getServer().getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION, !isUltraHardcore);

        Challenges.getInstance().getConfig().set("isUltraHardcore", isUltraHardcore);
        Challenges.getInstance().saveConfig();
    }

    public boolean isPVP() {
        return isPVP;
    }

    public void setPVP(boolean isPVP) {
        this.isPVP = isPVP;

        Challenges.getInstance().getServer().getWorld("world").setPVP(isPVP);

        Challenges.getInstance().getConfig().set("isPVP", isPVP);
        Challenges.getInstance().saveConfig();
    }

    public boolean isSplitHearts() {
        return isSplitHearts;
    }

    public void setSplitHearts(boolean splitHearts) {
        isSplitHearts = splitHearts;

        Challenges.getInstance().getConfig().set("isSplitHearts", isSplitHearts);
        Challenges.getInstance().saveConfig();
    }

    private void loadConfig() {
        isAllDieOnDeath = Challenges.getInstance().getConfig().getBoolean("isEveryoneDyingOnDeath");
        isUltraHardcore = Challenges.getInstance().getConfig().getBoolean("isUltraHardcore");
        isPVP = Challenges.getInstance().getConfig().getBoolean("isPVP");
        isSplitHearts = Challenges.getInstance().getConfig().getBoolean("isSplitHearts");
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
