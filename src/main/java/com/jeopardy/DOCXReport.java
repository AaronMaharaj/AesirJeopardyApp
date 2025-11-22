package com.jeopardy;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Generates a DOCX (Microsoft Word) summary report of a Jeopardy game.
 * Writes player names, gameplay details, and final scores to a DOCX file.
 */
public class DOCXReport implements ReportGenerator {

    /**
     * Generates a DOCX report for the given game and writes it to report.docx.
     * @param game the Game to generate a report for
     */
    @Override
    public void generateReport(Game game) {
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph title = document.createParagraph();
            XWPFRun titleRun = title.createRun();
            titleRun.setText("JEOPARDY PROGRAMMING GAME REPORT");
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            XWPFParagraph players = document.createParagraph();
            XWPFRun playersRun = players.createRun();
            playersRun.setText("Players: ");
            for (Player p : game.getPlayers()) {
                playersRun.setText(p.getName() + " ");
            }

            XWPFParagraph summaryHeader = document.createParagraph();
            XWPFRun summaryHeaderRun = summaryHeader.createRun();
            summaryHeaderRun.setText("Gameplay Summary:");
            summaryHeaderRun.setBold(true);

            List<GameEvent> events = game.getEventLog();
            int turn = 1;
            for (GameEvent event : events) {
                if (event.getActivity() == ActivityType.ANSWER_QUESTION) {
                    XWPFParagraph p = document.createParagraph();
                    XWPFRun r = p.createRun();
                    r.setText("Turn " + turn + ": " + getPlayerName(game, event.getPlayerId()) + " selected "
                            + event.getCategory() + " for " + event.getQuestionValue() + " pts");
                    r.addBreak();
                    r.setText("Answer: " + event.getAnswerGiven() + " - " + event.getResult());
                    r.addBreak();
                    r.setText("Score after turn: " + event.getScoreAfterPlay());
                    turn++;
                }
            }

            XWPFParagraph finalScores = document.createParagraph();
            XWPFRun finalScoresRun = finalScores.createRun();
            finalScoresRun.setText("Final Scores:");
            finalScoresRun.setBold(true);

            for (Player p : game.getPlayers()) {
                XWPFParagraph scoreP = document.createParagraph();
                XWPFRun scoreR = scoreP.createRun();
                scoreR.setText(p.getName() + ": " + p.getScore());
            }

            try (FileOutputStream out = new FileOutputStream("report.docx")) {
                document.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPlayerName(Game game, int playerId) {
        for (Player p : game.getPlayers()) {
            if (p.getId() == playerId)
                return p.getName();
        }
        return "Unknown";
    }
}
