package com.jeopardy;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a category of questions in a Jeopardy game.
 * Each category contains multiple questions of varying difficulty (value).
 */
public class Category {
    private String name;
    private List<Question> questions;

    /**
     * Constructs a Category with the given name.
     * @param name the category's name
     */
    public Category(String name) {
        this.name = name;
        this.questions = new ArrayList<>();
    }

    /**
     * Adds a question to this category.
     * @param question the Question to add
     */
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    /**
     * Retrieves the name of this category.
     * @return the category's name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves all questions in this category.
     * @return a List of Question objects
     */
    public List<Question> getQuestions() {
        return questions;
    }
    
    /**
     * Retrieves a question in this category by its point value.
     * @param value the point value of the question to find
     * @return the Question with the matching value, or null if not found
     */
    public Question getQuestion(int value) {
        for (Question q : questions) {
            if (q.getValue() == value) {
                return q;
            }
        }
        return null;
    }
}
