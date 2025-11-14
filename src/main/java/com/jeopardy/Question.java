package com.jeopardy;

import java.util.Map;

/**
 * Represents a question in a Jeopardy game.
 * Each question has text, a point value, multiple choice options, and a correct answer.
 */
public class Question {
    private String questionText;
    private int value;
    private Map<String, String> options;
    private String correctAnswer;
    private boolean answered;

    /**
     * Constructs a Question with the given parameters.
     * @param questionText the text of the question
     * @param value the point value of this question
     * @param options a Map of answer keys to answer text (e.g., "A" -> "Answer A")
     * @param correctAnswer the key of the correct answer (e.g., "A")
     */
    public Question(String questionText, int value, Map<String, String> options, String correctAnswer) {
        this.questionText = questionText;
        this.value = value;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.answered = false;
    }

    /**
     * Checks if the given answer matches the correct answer (case-insensitive).
     * @param answer the answer to check
     * @return true if the answer is correct, false otherwise
     */
    public boolean checkAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }

    /**
     * Retrieves the text of this question.
     * @return the question text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Retrieves the point value of this question.
     * @return the point value
     */
    public int getValue() {
        return value;
    }

    /**
     * Retrieves the answer options for this question.
     * @return a Map of answer keys to answer text
     */
    public Map<String, String> getOptions() {
        return options;
    }
    
    /**
     * Retrieves the correct answer key for this question.
     * @return the correct answer key
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Checks if this question has been answered during the game.
     * @return true if the question has been answered, false otherwise
     */
    public boolean isAnswered() {
        return answered;
    }

    /**
     * Sets whether this question has been answered.
     * @param answered true if the question has been answered, false otherwise
     */
    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
