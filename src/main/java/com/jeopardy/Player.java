package com.jeopardy;

/**
 * Represents a player in a Jeopardy game.
 * Tracks player identity, name, and score throughout the game.
 */
public class Player {
    private int id;
    private String name;
    private int score;

    /**
     * Constructs a Player with the given ID and name.
     * @param id the unique identifier for this player
     * @param name the player's name
     */
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
    }

    /**
     * Adds points to the player's current score.
     * Can accept negative values to deduct points.
     * @param amount the number of points to add (positive or negative)
     */
    public void addPoints(int amount) {
        this.score += amount;
    }

    /**
     * Retrieves the player's current score.
     * @return the player's current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Retrieves the player's unique identifier.
     * @return the player's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the player's name.
     * @return the player's name
     */
    public String getName() {
        return name;
    }
}
