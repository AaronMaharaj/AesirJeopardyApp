package com.jeopardy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads game data from CSV files.
 * Expects CSV format: Category, Value, Question, OptionA, OptionB, OptionC,
 * OptionD, CorrectAnswer
 */
public class CSVLoader implements GameDataLoader {

    /**
     * Loads categories and questions from a CSV file.
     * 
     * @param fileName the path to the CSV file to load
     * @return a List of Category objects parsed from the CSV
     */
    @Override
    public List<Category> load(String fileName) {
        List<Category> categories = new ArrayList<>();
        Map<String, Category> categoryMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (values.length < 8)
                    continue;

                String categoryName = values[0].trim();
                int value = Integer.parseInt(values[1].trim());
                String questionText = values[2].replace("\"", "").trim();
                String optionA = values[3].trim();
                String optionB = values[4].trim();
                String optionC = values[5].trim();
                String optionD = values[6].trim();
                String correctAnswer = values[7].trim();

                Category category = categoryMap.get(categoryName);
                if (category == null) {
                    category = new Category(categoryName);
                    categoryMap.put(categoryName, category);
                    categories.add(category);
                }

                Map<String, String> options = new HashMap<>();
                options.put("A", optionA);
                options.put("B", optionB);
                options.put("C", optionC);
                options.put("D", optionD);

                Question question = new Question(questionText, value, options, correctAnswer);
                category.addQuestion(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
