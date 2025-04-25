package com.github.challenges.listeners;

import com.github.challenges.Challenges;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashSet;
import java.util.Set;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (Challenges.getInstance().getChallenge().isAllDieOnDeath() && Challenges.getInstance().getChallenge().isRunning()) {
            Challenges.getInstance().getChallenge().reset();
            Player deadPlayer = event.getPlayer();


            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getName().equals(deadPlayer.getName())) player.setHealth(0);

                player.setGameMode(GameMode.SPECTATOR);
            }

        }

        // Timer pausieren
        Challenges.getInstance().getChallenge().pause();

        // Alle offenen Fragen für den gestorbenen Spieler entfernen
        Challenges.getInstance().getChallenge()
                .getCurrentQuestions()
                .remove(event.getEntity().getUniqueId());

    }
}

