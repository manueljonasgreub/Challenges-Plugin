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
            questions.addAll(loaded);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Question getRandomQuestion() {
        return questions.get(random.nextInt(questions.size()));
    }
}
