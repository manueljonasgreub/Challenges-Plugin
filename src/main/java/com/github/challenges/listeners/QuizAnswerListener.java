package com.github.challenges.listeners;

import com.github.challenges.challenge.quiz.Question;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.UUID;

public class QuizAnswerListener implements Listener {

    private final Map<UUID, Question> currentQuestions;

    public QuizAnswerListener(Map<UUID, Question> currentQuestions) {
        this.currentQuestions = currentQuestions;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Question question = currentQuestions.get(player.getUniqueId());

        if (question == null) return;

        String input = event.getMessage().trim();

        if (input.equals(question.correctAnswer)) {
            player.sendMessage("§aRichtig!");
        } else {
            player.sendMessage("§cFalsch! Die richtige Antwort war: " + question.correctAnswer);
            // Spieler sofort töten
            player.setHealth(0.0);
        }

        // Frage freigeben und Chat-Nachricht verbergen
        currentQuestions.remove(player.getUniqueId());
        event.setCancelled(true);
    }
}
