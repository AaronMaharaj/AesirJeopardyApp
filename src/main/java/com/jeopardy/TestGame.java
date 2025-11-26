package com.jeopardy;

public class TestGame {
    public static void main(String[] args) {
        System.out.println("Starting Test Game...");
        Game game = new Game();

        // 1. Load Data
        System.out.println("Loading JSON...");
        game.loadGameData("sample_game_JSON.json");

        if (game.getCategories().isEmpty()) {
            System.out.println("FAILED: No categories loaded.");
            return;
        }
        System.out.println("Loaded " + game.getCategories().size() + " categories.");

        // 2. Add Players
        game.addPlayer("Alice");
        game.addPlayer("Bob");
        game.startGame();

        // 3. Play a turn
        Player p1 = game.getCurrentPlayer();
        System.out.println("Current Player: " + p1.getName());

        Category cat = game.getCategories().get(0);
        System.out.println("Selecting Category: " + cat.getName());
        game.selectCategory(cat.getName());

        Question q = cat.getQuestions().get(0);
        System.out.println("Selecting Question: " + q.getValue());
        game.selectQuestion(q.getValue());

        System.out.println("Answering Correctly: " + q.getCorrectAnswer());
        game.answerQuestion(q.getCorrectAnswer());

        System.out.println("Score for " + p1.getName() + ": " + p1.getScore());
        if (p1.getScore() != q.getValue()) {
            System.out.println("FAILED: Score incorrect.");
        }

        // 4. Play another turn
        Player p2 = game.getCurrentPlayer();
        System.out.println("Current Player: " + p2.getName());

        Category cat2 = game.getCategories().get(1);
        game.selectCategory(cat2.getName());
        Question q2 = cat2.getQuestions().get(0);
        game.selectQuestion(q2.getValue());

        System.out.println("Answering Incorrectly: Z");
        game.answerQuestion("Z");

        System.out.println("Score for " + p2.getName() + ": " + p2.getScore());

        // 5. Generate Reports
        System.out.println("Generating Reports...");
        game.generateSummaryReport("TXT");
        game.generateSummaryReport("PDF");
        game.generateSummaryReport("DOCX");

        System.out.println("Test Complete.");
    }
}
