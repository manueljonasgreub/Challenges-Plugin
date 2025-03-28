package com.github.challenges.challenge.quiz;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class QuestionManager {
    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();

    public void load(File file) {
        try (Reader reader = new FileReader(file)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            List<Question> loaded = new Gson().fromJson(json.get("questions"), new TypeToken<List<Question>>() {}.getType());
            questions.clear();
            System.out.println("[Questions] Lade Fragen von Datei: " + file.getAbsolutePath());
            questions.addAll(loaded);
            System.out.println("[Questions] Anzahl geladener Fragen: " + questions.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Question getRandomQuestion() {
        if (questions == null || questions.isEmpty()) {
            System.out.println("[Questions] Keine Fragen geladen!");
            return null;
        }
        return questions.get(random.nextInt(questions.size()));
    }

}
