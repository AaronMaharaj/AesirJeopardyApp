package com.jeopardy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a Jeopardy game session.
 * Manages players, categories, questions, scoring, and event notifications.
 */
public class Game {
    private Player currentPlayer;
    private List<Player> players;
    private List<Category> categories;
    private EventManager eventManager;
    private List<GameEvent> eventLog;
    private String currentCaseId;

    // Current state tracking
    private int currentPlayerIndex = 0;
    private Category selectedCategory;
    private Question selectedQuestion;

    /**
     * Constructs a Game instance.
     * Initializes empty player and category lists, sets up event management and
     * logging.
     */
    public Game() {
        this.players = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.eventLog = new ArrayList<>();
        this.eventManager = new EventManager(ActivityType.values());

        // Default observer
        GameEventLogger logger = new GameEventLogger();
        for (ActivityType type : ActivityType.values()) {
            this.eventManager.subscribe(type, logger);
        }

        // Also subscribe internal logger to keep track of events in memory for
        // reporting
        Listener internalLogger = event -> eventLog.add(event);
        for (ActivityType type : ActivityType.values()) {
            this.eventManager.subscribe(type, internalLogger);
        }

        this.currentCaseId = "GAME" + System.currentTimeMillis(); // Simple ID generation
    }

    /**
     * Loads game data from a file.
     * Determines the file format and uses the appropriate loader.
     * 
     * @param fileName the path to the file to load
     */
    public void loadGameData(String fileName) {
        notify(ActivityType.LOAD_FILE, 0, "Loading file: " + fileName, null, 0);
        DataLoaderFactory factory = new DataLoaderFactory();
        GameDataLoader loader = factory.getLoader(fileName);
        if (loader != null) {
            this.categories = loader.load(fileName);
            notify(ActivityType.FILE_LOADED_SUCCESSFULLY, 0, "Loaded " + categories.size() + " categories", null, 0);
        } else {
            System.out.println("Error: Unsupported file format.");
        }
    }

    /**
     * Adds a new player to the game.
     * 
     * @param name the name of the player to add
     */
    public void addPlayer(String name) {
        Player player = new Player(players.size() + 1, name);
        players.add(player);
        notify(ActivityType.ENTER_PLAYER_NAME, player.getId(), "Added player: " + name, null, 0);
    }

    /**
     * Starts the game with the added players.
     * Sets the first player as the current player.
     */
    public void startGame() {
        if (players.isEmpty()) {
            System.out.println("No players added.");
            return;
        }
        currentPlayer = players.get(0);
        notify(ActivityType.START_GAME, 0, "Game Started", null, 0);
    }

    /**
     * Selects a category by name for the current player.
     * 
     * @param categoryName the name of the category to select
     * @return true if the category exists and was selected, false otherwise
     */
    public boolean selectCategory(String categoryName) {
        for (Category c : categories) {
            if (c.getName().equalsIgnoreCase(categoryName)) {
                selectedCategory = c;
                notify(ActivityType.SELECT_CATEGORY, currentPlayer.getId(), categoryName, null, 0);
                return true;
            }
        }
        return false;
    }

    /**
     * Selects a question from the current category by its point value.
     * 
     * @param value the point value of the question to select
     * @return the selected Question if it exists and hasn't been answered, null
     *         otherwise
     */
    public Question selectQuestion(int value) {
        if (selectedCategory == null) {
            return null;
        }
        Question q = selectedCategory.getQuestion(value);
        if (q != null && !q.isAnswered()) {
            selectedQuestion = q;
            notify(ActivityType.SELECT_QUESTION, currentPlayer.getId(), selectedCategory.getName(), null, value);
            return q;
        } else {
            return null;
        }
    }

    /**
     * Submits an answer for the currently selected question.
     * Updates the player's score if correct and marks the question as answered.
     * Advances to the next player.
     * 
     * @param answer the answer provided by the player
     */
    public void answerQuestion(String answer) {
        if (selectedQuestion == null) {
            return;
        }

        boolean correct = selectedQuestion.checkAnswer(answer);
        String result = correct ? "Correct" : "Incorrect";
        int points = correct ? selectedQuestion.getValue() : -selectedQuestion.getValue();

        if (correct) {
            currentPlayer.addPoints(selectedQuestion.getValue());
        }

        selectedQuestion.setAnswered(true);

        notify(ActivityType.ANSWER_QUESTION, currentPlayer.getId(), selectedCategory.getName(), answer,
                selectedQuestion.getValue(), result, currentPlayer.getScore());
        notify(ActivityType.SCORE_UPDATED, currentPlayer.getId(), "Score updated", null, currentPlayer.getScore());

        // Next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);

        selectedCategory = null;
        selectedQuestion = null;
    }

    /**
     * Generates a summary report of the game in the specified format.
     * 
     * @param format the report format ("TXT", "PDF", or "DOCX")
     */
    public void generateSummaryReport(String format) {
        ReportGenerator generator = null;
        if (format.equalsIgnoreCase("TXT")) {
            generator = new TXTReport();
        } else if (format.equalsIgnoreCase("PDF")) {
            generator = new PDFReport();
        } else if (format.equalsIgnoreCase("DOCX")) {
            generator = new DOCXReport();
        }

        if (generator != null) {
            generator.generateReport(this);
            notify(ActivityType.GENERATE_REPORT, 0, "Generated " + format + " report", null, 0);
        }
    }

    private void notify(ActivityType type, int playerId, String details, String answer, int value) {
        notify(type, playerId, details, answer, value, null, 0);
    }

    private void notify(ActivityType type, int playerId, String category, String answer, int value, String result,
            int score) {
        GameEvent event = new GameEvent(
                currentCaseId,
                playerId,
                type,
                LocalDateTime.now(),
                category, // reusing category field for details/category
                value,
                answer,
                result,
                score);
        eventManager.notify(type, event);
    }

    /**
     * Retrieves all players in the game.
     * 
     * @return a List of Player objects
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Retrieves all categories in the game.
     * 
     * @return a List of Category objects
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Retrieves the event log for this game session.
     * 
     * @return a List of GameEvent objects that occurred during the game
     */
    public List<GameEvent> getEventLog() {
        return eventLog;
    }

    /**
     * Retrieves the current player.
     * 
     * @return the current Player object
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
