package com.jeopardy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads game data from JSON files.
 * Expects JSON array format with objects containing Category, Value, Question, Options, and CorrectAnswer.
 */
public class JSONLoader implements GameDataLoader {

    /**
     * Loads categories and questions from a JSON file.
     * @param fileName the path to the JSON file to load
     * @return a List of Category objects parsed from the JSON
     */
    @Override
    public List<Category> load(String fileName) {
        List<Category> categories = new ArrayList<>();
        Map<String, Category> categoryMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(fileName)) {
            JSONTokener tokener = new JSONTokener(fis);
            JSONArray jsonArray = new JSONArray(tokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String categoryName = obj.getString("Category");
                int value = obj.getInt("Value");
                String questionText = obj.getString("Question");
                JSONObject optionsObj = obj.getJSONObject("Options");
                String correctAnswer = obj.getString("CorrectAnswer");

                Category category = categoryMap.get(categoryName);
                if (category == null) {
                    category = new Category(categoryName);
                    categoryMap.put(categoryName, category);
                    categories.add(category);
                }

                Map<String, String> options = new HashMap<>();
                options.put("A", optionsObj.getString("A"));
                options.put("B", optionsObj.getString("B"));
                options.put("C", optionsObj.getString("C"));
                options.put("D", optionsObj.getString("D"));

                Question question = new Question(questionText, value, options, correctAnswer);
                category.addQuestion(question);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
