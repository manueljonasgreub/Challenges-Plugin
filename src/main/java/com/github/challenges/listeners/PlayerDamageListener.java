package com.github.challenges.listeners;

import com.github.challenges.Challenges;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.net.http.WebSocket;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (!Challenges.getInstance().getChallenge().isRunning()) {
                event.setCancelled(true);
            }
        }
    }

}
