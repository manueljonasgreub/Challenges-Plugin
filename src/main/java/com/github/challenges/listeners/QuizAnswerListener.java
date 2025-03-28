package com.github.challenges.listeners;

import com.github.challenges.challenge.quiz.Question;
import org.bukkit.ChatColor;
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
        Question q = currentQuestions.get(player.getUniqueId());

        if (q != null) {
            String msg = event.getMessage().trim();

            if (msg.equals(q.correctAnswer)) {
                player.sendMessage(ChatColor.GREEN + "✅ Richtig!");
            } else {
                player.sendMessage(ChatColor.RED + "❌ Falsch. Richtig wäre: " + q.correctAnswer);
            }

            currentQuestions.remove(player.getUniqueId());
            event.setCancelled(true);
        }
    }
}
