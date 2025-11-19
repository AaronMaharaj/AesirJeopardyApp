package com.jeopardy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages event subscription and notification for a Jeopardy game.
 * Implements the Observer pattern to allow multiple listeners to receive game events.
 */
public class EventManager {
    private Map<ActivityType, List<Listener>> listeners = new HashMap<>();

    /**
     * Constructs an EventManager with the given initial operation types.
     * @param operations the ActivityTypes to initialize the manager with
     */
    public EventManager(ActivityType... operations) {
        for (ActivityType operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    /**
     * Subscribes a listener to events of a specific type.
     * If the type is not yet registered, it will be added automatically.
     * @param type the ActivityType to subscribe to
     * @param listener the Listener to be notified of events
     */
    public void subscribe(ActivityType type, Listener listener) {
        List<Listener> users = listeners.get(type);
        if (users == null) {
            users = new ArrayList<>();
            listeners.put(type, users);
        }
        users.add(listener);
    }

    /**
     * Unsubscribes a listener from events of a specific type.
     * @param type the ActivityType to unsubscribe from
     * @param listener the Listener to remove
     */
    public void unsubscribe(ActivityType type, Listener listener) {
        List<Listener> users = listeners.get(type);
        if (users != null) {
            users.remove(listener);
        }
    }

    /**
     * Notifies all subscribed listeners of an event of the given type.
     * @param type the ActivityType of the event
     * @param event the GameEvent to notify listeners about
     */
    public void notify(ActivityType type, GameEvent event) {
        List<Listener> users = listeners.get(type);
        if (users != null) {
            for (Listener listener : users) {
                listener.update(event);
            }
        }
    }
}
