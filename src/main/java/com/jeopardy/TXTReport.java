package com.jeopardy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Generates a text-based summary report of a Jeopardy game.
 * Writes player names, gameplay details, and final scores to report.txt.
 */
public class TXTReport implements ReportGenerator {

    /**
     * Generates a text report for the given game and writes it to report.txt.
     * @param game the Game to generate a report for
     */
    @Override
    public void generateReport(Game game) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("report.txt"))) {
            writer.println("JEOPARDY PROGRAMMING GAME REPORT");
            writer.println("================================");
            writer.println();

            // Assuming we can get players from game
            writer.print("Players: ");
            for (Player p : game.getPlayers()) {
                writer.print(p.getName() + " ");
            }
            writer.println();
            writer.println();

            writer.println("Gameplay Summary:");
            writer.println("-----------------");

            List<GameEvent> events = game.getEventLog();
            int turn = 1;
            for (GameEvent event : events) {
                if (event.getActivity() == ActivityType.ANSWER_QUESTION) {
                    writer.println("Turn " + turn + ": " + getPlayerName(game, event.getPlayerId()) + " selected "
                            + event.getCategory() + " for " + event.getQuestionValue() + " pts");
                    writer.println("Question: " + getQuestionText(game, event.getCategory(), event.getQuestionValue())); // This
                                                                                                                         // might
                                                                                                                         // be
                                                                                                                         // hard
                                                                                                                         // if
                                                                                                                         // we
                                                                                                                         // don't
                                                                                                                         // store
                                                                                                                         // question
                                                                                                                         // text
                                                                                                                         // in
                                                                                                                         // event
                    // Actually, the sample report shows question text. We should probably store it
                    // in event or look it up.
                    // The event log has category and value. We can look up the question in the game
                    // categories.
                    writer.println("Answer: " + event.getAnswerGiven() + " — " + event.getResult() + " (+"
                            + (event.getResult().equals("Correct") ? event.getQuestionValue() : "0") + " pts)");
                    writer.println("Score after turn: " + getPlayerName(game, event.getPlayerId()) + " = "
                            + event.getScoreAfterPlay());
                    writer.println();
                    turn++;
                }
            }

            writer.println("Final Scores:");
            for (Player p : game.getPlayers()) {
                writer.println(p.getName() + ": " + p.getScore());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the name of a player by their ID.
     * @param game the Game containing the players
     * @param playerId the ID of the player to find
     * @return the player's name, or "Unknown" if not found
     */
    private String getPlayerName(Game game, int playerId) {
        for (Player p : game.getPlayers()) {
            if (p.getId() == playerId)
                return p.getName();
        }
        return "Unknown";
    }

    /**
     * Retrieves the text of a question from a specific category by its value.
     * @param game the Game containing the categories
     * @param categoryName the name of the category
     * @param value the point value of the question
     * @return the question text, or "Unknown Question" if not found
     */
    private String getQuestionText(Game game, String categoryName, int value) {
        for (Category c : game.getCategories()) {
            if (c.getName().equals(categoryName)) {
                Question q = c.getQuestion(value);
                if (q != null)
                    return q.getQuestionText();
            }
        }
        return "Unknown Question";
    }
}
