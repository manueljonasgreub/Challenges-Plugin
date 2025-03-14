package com.github.challenges.challenge;

import com.github.challenges.Challenges;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Challenge {

    private int time = 0;
    private boolean isRunning = false;

    public Challenge(){
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
}
