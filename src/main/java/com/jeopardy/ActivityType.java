package com.jeopardy;

/**
 * Enumeration of all activity types that can occur during a Jeopardy game.
 * These types are used to categorize and log game events.
 */
public enum ActivityType {
    START_GAME,
    LOAD_FILE,
    FILE_LOADED_SUCCESSFULLY,
    SELECT_PLAYER_COUNT,
    ENTER_PLAYER_NAME,
    SELECT_CATEGORY,
    SELECT_QUESTION,
    ANSWER_QUESTION,
    SCORE_UPDATED,
    GENERATE_REPORT,
    GENERATE_EVENT_LOG,
    EXIT_GAME
}
