package com.jeopardy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Jeopardy!");

        // 1. Load Data
        while (true) {
            System.out.println("Enter file to load (e.g., sample_game_JSON.json):");
            String fileName = scanner.nextLine().trim();
            if (fileName.isEmpty()) {
                System.out.println("Filename cannot be empty.");
                continue;
            }
            try {
                game.loadGameData(fileName);
                if (!game.getCategories().isEmpty()) {
                    break;
                } else {
                    System.out.println("Failed to load categories. Please check the file and try again.");
                }
            } catch (Exception e) {
                System.out.println("Error loading file: " + e.getMessage());
            }
        }

        // 2. Setup Players
        int numPlayers = 0;
        while (true) {
            System.out.println("Enter number of players (1-4):");
            try {
                String input = scanner.nextLine();
                numPlayers = Integer.parseInt(input);
                if (numPlayers >= 1 && numPlayers <= 4) {
                    break;
                } else {
                    System.out.println("Please enter a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }

        for (int i = 0; i < numPlayers; i++) {
            while (true) {
                System.out.println("Enter name for Player " + (i + 1) + ":");
                String name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    game.addPlayer(name);
                    break;
                }
                System.out.println("Name cannot be empty.");
            }
        }

        game.startGame();

        // 3. Game Loop
        boolean running = true;
        while (running) {
            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("\n------------------------------------------------");
            System.out.println(
                    "Current Player: " + currentPlayer.getName() + " (Score: " + currentPlayer.getScore() + ")");
            System.out.println("------------------------------------------------");
            System.out.println("Available Categories:");

            boolean anyQuestionAvailable = false;
            for (Category c : game.getCategories()) {
                System.out.print(c.getName() + " [");
                for (Question q : c.getQuestions()) {
                    if (!q.isAnswered()) {
                        System.out.print(q.getValue() + " ");
                        anyQuestionAvailable = true;
                    } else {
                        System.out.print("X ");
                    }
                }
                System.out.println("]");
            }

            if (!anyQuestionAvailable) {
                System.out.println("\nAll questions answered! Game Over.");
                running = false;
                break;
            }

            while (true) {
                System.out.println("\nEnter category to select (or 'exit' to quit):");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("exit")) {
                    running = false;
                    break;
                }

                if (game.selectCategory(input)) {
                    break;
                } else {
                    System.out.println("Category not found.");
                }
            }

            if (!running)
                break;
            // Check if category selection was successful (we might need to update Game to
            // return boolean or check state)
            // For now, we rely on the console output from Game, but better to check if a
            // category is actually selected.
            // Since Game.selectCategory prints "Category not found" but doesn't return
            // status, we can check internal state if we had access,
            // or just proceed. The Game class handles invalid selection by not setting
            // selectedCategory.

            while (true) {
                System.out.println("Enter question value:");
                int value = 0;
                try {
                    String valInput = scanner.nextLine();
                    value = Integer.parseInt(valInput);
                    Question q = game.selectQuestion(value);
                    if (q != null) {
                        System.out.println("Question: " + q.getQuestionText());
                        System.out.println("Options:");
                        q.getOptions().forEach((k, v) -> System.out.println(k + ": " + v));
                        break;
                    } else {
                        System.out.println("Question not available or already answered.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value. Please enter a number.");
                    continue;
                }
            }

            while (true) {
                System.out.println("Enter your answer (Option A, B, C, or D):");
                String answer = scanner.nextLine().trim().toUpperCase();
                if (answer.matches("[ABCD]")) {
                    game.answerQuestion(answer);
                    break;
                } else {
                    System.out.println("Invalid answer. Please enter A, B, C, or D.");
                }
            }
        }

        // 4. Report
        System.out.println("\nGame Over!");
        while (true) {
            System.out.println("Generate report? (TXT/PDF/DOCX) or 'skip':");
            String format = scanner.nextLine().trim();
            if (format.equalsIgnoreCase("skip")) {
                break;
            }
            if (format.equalsIgnoreCase("TXT") || format.equalsIgnoreCase("PDF") || format.equalsIgnoreCase("DOCX")) {
                game.generateSummaryReport(format);
                break; // Or allow generating multiple? Let's break after one for simplicity or ask
                       // again.
            } else {
                System.out.println("Invalid format. Please enter TXT, PDF, DOCX, or skip.");
            }
        }

        scanner.close();
    }
}
