package com.jeopardy;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

/**
 * Generates a PDF summary report of a Jeopardy game.
 * Writes player names, gameplay details, and final scores to a PDF file.
 */
public class PDFReport implements ReportGenerator {

    /**
     * Generates a PDF report for the given game and writes it to report.pdf.
     * @param game the Game to generate a report for
     */
    @Override
    public void generateReport(Game game) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("JEOPARDY PROGRAMMING GAME REPORT");
            contentStream.newLine();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.showText("================================");
            contentStream.newLine();
            contentStream.newLine();

            contentStream.showText("Players: ");
            for (Player p : game.getPlayers()) {
                contentStream.showText(p.getName() + " ");
            }
            contentStream.newLine();
            contentStream.newLine();

            contentStream.showText("Gameplay Summary:");
            contentStream.newLine();
            contentStream.showText("-----------------");
            contentStream.newLine();

            List<GameEvent> events = game.getEventLog();
            int turn = 1;
            int lines = 8; // Header lines

            for (GameEvent event : events) {
                if (event.getActivity() == ActivityType.ANSWER_QUESTION) {
                    if (lines > 45) { // Approx lines per page
                        contentStream.endText();
                        contentStream.close();

                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.beginText();
                        contentStream.setLeading(14.5f);
                        contentStream.newLineAtOffset(50, 750);
                        lines = 0;
                    }

                    contentStream.showText("Turn " + turn + ": " + getPlayerName(game, event.getPlayerId())
                            + " selected " + event.getCategory() + " for " + event.getQuestionValue() + " pts");
                    contentStream.newLine();
                    contentStream.showText("Answer: " + event.getAnswerGiven() + " - " + event.getResult());
                    contentStream.newLine();
                    contentStream.showText("Score after turn: " + event.getScoreAfterPlay());
                    contentStream.newLine();
                    contentStream.newLine();
                    turn++;
                    lines += 4;
                }
            }

            if (lines > 45) {
                contentStream.endText();
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);
            }

            contentStream.showText("Final Scores:");
            contentStream.newLine();
            for (Player p : game.getPlayers()) {
                contentStream.showText(p.getName() + ": " + p.getScore());
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            document.save("report.pdf");
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
