package com.jeopardy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

/**
 * Logs game events to a CSV file.
 * Implements the Listener interface to receive GameEvent updates and writes them to game_event_log.csv.
 */
public class GameEventLogger implements Listener {
    private String logFileName = "game_event_log.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Constructs a GameEventLogger.
     * Initializes the CSV log file for appending events.
     */
    public GameEventLogger() {
        // Initialize file with header if it doesn't exist or just append?
        // The diagram says "Writes to game_event_log.csv"
        // Let's write the header if we are starting a new session or just append.
        // For simplicity, we'll append, but maybe we should check if file exists to
        // write header.
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFileName, true))) {
            // Check if file is empty to write header?
            // For now, let's assume we just append.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a game event to the CSV log file.
     * @param event the GameEvent to log
     */
    @Override
    public void update(GameEvent event) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFileName, true))) {
            // Format: CaseID, PlayerID, Activity, Timestamp, Category, QuestionValue,
            // AnswerGiven, Result, ScoreAfter
            StringBuilder sb = new StringBuilder();
            sb.append(event.getCaseId()).append(",");
            sb.append(event.getPlayerId()).append(",");
            sb.append(event.getActivity()).append(",");
            sb.append(event.getTimestamp().format(formatter)).append(",");
            sb.append(event.getCategory() != null ? event.getCategory() : "").append(",");
            sb.append(event.getQuestionValue()).append(",");
            sb.append(event.getAnswerGiven() != null ? event.getAnswerGiven().replace(",", ";") : "").append(","); // Escape
                                                                                                                   // commas
            sb.append(event.getResult() != null ? event.getResult() : "").append(",");
            sb.append(event.getScoreAfterPlay());

            writer.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
