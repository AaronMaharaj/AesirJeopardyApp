package com.jeopardy;

import java.time.LocalDateTime;

/**
 * Represents an event that occurs during a Jeopardy game.
 * GameEvents are used to track all activities and enable event-driven logging and reporting.
 */
public class GameEvent {
    private String caseId;
    private int playerId;
    private ActivityType activity;
    private LocalDateTime timestamp;
    private String category;
    private int questionValue;
    private String answerGiven;
    private String result;
    private int scoreAfterPlay;

    /**
     * Constructs a GameEvent with the given parameters.
     * @param caseId the unique identifier for the game session
     * @param playerId the ID of the player involved in this event
     * @param activity the type of activity that occurred
     * @param timestamp the time when this event occurred
     * @param category the category name (if applicable to this event)
     * @param questionValue the point value of the question (if applicable)
     * @param answerGiven the answer provided by the player (if applicable)
     * @param result the result of the action (e.g., "Correct", "Incorrect")
     * @param scoreAfterPlay the player's score after this event
     */
    public GameEvent(String caseId, int playerId, ActivityType activity, LocalDateTime timestamp,
            String category, int questionValue, String answerGiven, String result, int scoreAfterPlay) {
        this.caseId = caseId;
        this.playerId = playerId;
        this.activity = activity;
        this.timestamp = timestamp;
        this.category = category;
        this.questionValue = questionValue;
        this.answerGiven = answerGiven;
        this.result = result;
        this.scoreAfterPlay = scoreAfterPlay;
    }

    /**
     * Retrieves the case ID for this game session.
     * @return the case ID
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * Retrieves the player ID associated with this event.
     * @return the player ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Retrieves the type of activity that occurred.
     * @return the ActivityType
     */
    public ActivityType getActivity() {
        return activity;
    }

    /**
     * Retrieves the timestamp of this event.
     * @return the LocalDateTime when this event occurred
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Retrieves the category associated with this event.
     * @return the category name
     */
    public String getCategory() {
        return category;
    }

    /**
     * Retrieves the question value (point value) associated with this event.
     * @return the question value
     */
    public int getQuestionValue() {
        return questionValue;
    }

    /**
     * Retrieves the answer provided by the player.
     * @return the answer text
     */
    public String getAnswerGiven() {
        return answerGiven;
    }

    /**
     * Retrieves the result of this event action.
     * @return the result string (e.g., "Correct", "Incorrect")
     */
    public String getResult() {
        return result;
    }

    /**
     * Retrieves the player's score after this event.
     * @return the player's score
     */
    public int getScoreAfterPlay() {
        return scoreAfterPlay;
    }
}
