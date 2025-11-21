package com.jeopardy;

/**
 * Interface for report generation strategies.
 * Implementing classes generate different formats of game summary reports.
 */
public interface ReportGenerator {
    /**
     * Generates a report for the given game.
     * @param game the Game object containing game state and events to report on
     */
    void generateReport(Game game);
}
