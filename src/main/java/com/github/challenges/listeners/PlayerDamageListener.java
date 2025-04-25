package com.github.challenges.listeners;

import com.github.challenges.Challenges;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.net.http.WebSocket;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (!Challenges.getInstance().getChallenge().isRunning()) {
            event.setCancelled(true);
            return;
        }


        if (Challenges.getInstance().getChallenge().isSplitHearts()) {
            Player damagedPlayer = (Player) event.getEntity();
            double newHealth = damagedPlayer.getHealth() - event.getFinalDamage();
            syncPlayerHealth(newHealth, ((Player) event.getEntity()).getPlayer());
        }

    }

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (Challenges.getInstance().getChallenge().isSplitHearts()) {
            Player healedPlayer = (Player) event.getEntity();
            double newHealth = healedPlayer.getHealth() + event.getAmount();
            syncPlayerHealth(newHealth, ((Player) event.getEntity()).getPlayer());
        }

    }

    private void syncPlayerHealth(double newHealth, Player damagedPlayer) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getName().equals(damagedPlayer.getName())) {
                player.setHealth(Math.max(0, Math.min(newHealth, player.getAttribute(Attribute.MAX_HEALTH).getValue())));
            }
        }
    }

}