package com.github.challenges.challenge;

import com.github.challenges.challenge.quiz.QuestionManager;
import com.github.challenges.challenge.quiz.Question;

import com.github.challenges.Challenges;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Challenge {

    private int time = 0;
    private boolean isRunning = false;
    private boolean isAllDieOnDeath;

    private QuestionManager questionManager = new QuestionManager();
    private Map<UUID, Question> currentQuestions = new HashMap<>();
    private boolean questionsActive = false;

    public Challenge(){

        loadConfig();

        File target = new File(Challenges.getInstance().getDataFolder(), "Questions.json");

        if (!target.exists()) {
            Challenges.getInstance().saveResource("Questions.json", false);
        }
        questionManager.load(target);

        // ⏱️ Timer-Scheduler (jede Sekunde)
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Challenges.getInstance(),
                () -> {
                    printTime();

                    if (!isRunning) return;

                    time++;
                },
                0,
                20 // 1 Sekunde = 20 Ticks
        );

        // ❓ Fragen-Scheduler (alle 5–7 Minuten)
        Bukkit.getScheduler().runTaskTimer(Challenges.getInstance(), () -> {
            if (questionsActive) {
                Question q = questionManager.getRandomQuestion();
                if (q != null) {
                    Bukkit.broadcast(Component.text("📚 Frage: " + q.question).color(NamedTextColor.AQUA));
                    Bukkit.broadcast(Component.text("1️⃣ " + q.answer1));
                    Bukkit.broadcast(Component.text("2️⃣ " + q.answer2));
                    Bukkit.broadcast(Component.text("3️⃣ " + q.answer3));
                    Bukkit.broadcast(Component.text("4️⃣ " + q.answer4));

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        currentQuestions.put(player.getUniqueId(), q);
                    }
                }
            }
        }, 20L * 60 * 5, 20L * 60 * (5 + new Random().nextInt(3))); // 5–7 Minuten
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

    public boolean areQuestionsActive() {
        return questionsActive;
    }

    public void toggleQuestions() {
        this.questionsActive = !this.questionsActive;
    }

    public Map<UUID, Question> getCurrentQuestions() {
        return currentQuestions;
    }

    private void loadConfig() {
        isAllDieOnDeath = Challenges.getInstance().getConfig().getBoolean("isEveryoneDyingOnDeath");
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

}
