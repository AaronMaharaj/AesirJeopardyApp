package com.jeopardy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

public class EventLoggerTest {

    @Test
    public void testGameEventLoggerWritesCsvLine() throws IOException {
        Path logPath = Path.of("game_event_log.csv");
        Path backup = null;
        if (Files.exists(logPath)) {
            backup = Files.createTempFile("game_event_log_backup", ".csv");
            Files.copy(logPath, backup, StandardCopyOption.REPLACE_EXISTING);
            Files.delete(logPath);
        }

        try {
            GameEventLogger logger = new GameEventLogger();

            String caseId = "TESTCASE123";
            int playerId = 7;
            ActivityType activity = ActivityType.ANSWER_QUESTION;
            LocalDateTime now = LocalDateTime.now();
            String category = "UnitTestCategory";
            int questionValue = 250;
            String answerGiven = "B";
            String result = "Correct";
            int scoreAfter = 250;

            GameEvent event = new GameEvent(caseId, playerId, activity, now, category, questionValue, answerGiven,
                    result, scoreAfter);

            logger.update(event);

            assertTrue(Files.exists(logPath), "Log file should exist after update");
            //not the neatest way to assert this but it works :D
            String lastLine = Files.readAllLines(logPath).stream().reduce((first, second) -> second).orElse(""); 
            assertTrue(lastLine.contains(caseId), "CSV should contain caseId");
            assertTrue(lastLine.contains(String.valueOf(playerId)), "CSV should contain playerId");
            assertTrue(lastLine.contains(activity.toString()), "CSV should contain activity");
            assertTrue(lastLine.contains(category), "CSV should contain category");
            assertTrue(lastLine.contains(String.valueOf(questionValue)), "CSV should contain question value");
            assertTrue(lastLine.contains(answerGiven), "CSV should contain answer given");
            assertTrue(lastLine.contains(result), "CSV should contain result");
            assertTrue(lastLine.contains(String.valueOf(scoreAfter)), "CSV should contain score after");

        } finally { //cleanup stuff
            if (Files.exists(logPath)) {
                Files.delete(logPath);
            }
            if (backup != null) {
                Files.copy(backup, logPath, StandardCopyOption.REPLACE_EXISTING);
                Files.delete(backup);
            }
        }
    }
}
