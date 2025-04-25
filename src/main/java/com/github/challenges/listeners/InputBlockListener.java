package com.github.challenges.listeners;

import com.github.challenges.challenge.quiz.Question;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.Map;
import java.util.UUID;

public class InputBlockListener implements Listener {

    private final Map<UUID, Question> currentQuestions;

    public InputBlockListener(Map<UUID, Question> currentQuestions) {
        this.currentQuestions = currentQuestions;
    }

    // Bewegung blockieren
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (currentQuestions.containsKey(event.getPlayer().getUniqueId())) {
            // Spieler bleibt stehen
            event.setTo(event.getFrom());
        }
    }

    // Interaktionen (z.B. Rechtsklick) blockieren
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (currentQuestions.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    // Items droppen blockieren
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (currentQuestions.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    // Alle Befehle außer Chat blockieren
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (currentQuestions.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cWährend einer Quizfrage kannst du nur über den Chat antworten!");
        }
    }

}
