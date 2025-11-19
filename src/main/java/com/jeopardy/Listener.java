package com.jeopardy;

/**
 * Interface for listeners that observe game events.
 * Implementing classes will be notified of game activities via the update method.
 */
public interface Listener {
    /**
     * Called when a game event occurs.
     * @param event the GameEvent that occurred
     */
    void update(GameEvent event);
}
