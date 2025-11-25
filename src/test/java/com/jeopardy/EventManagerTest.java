package com.jeopardy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class EventManagerTest { // tests subscribe/notify/unsubscribe logic for EventManager

    @Test
    void testSubscribeAndNotify() {
        EventManager manager = new EventManager(ActivityType.START_GAME);
        List<GameEvent> received = new ArrayList<>();
        Listener listener = event -> received.add(event);

        manager.subscribe(ActivityType.START_GAME, listener);

        GameEvent event = new GameEvent("CASE1", 1, ActivityType.START_GAME, LocalDateTime.now(), "cat", 0, null,
                null, 0);
        manager.notify(ActivityType.START_GAME, event);

        assertEquals(1, received.size());
        assertSame(event, received.get(0));
    }

    @Test
    void testUnsubscribeStopsNotifications() {
        EventManager manager = new EventManager(ActivityType.START_GAME);
        List<GameEvent> receivedA = new ArrayList<>();
        List<GameEvent> receivedB = new ArrayList<>();
        Listener a = event -> receivedA.add(event);
        Listener b = event -> receivedB.add(event);

        manager.subscribe(ActivityType.START_GAME, a);
        manager.subscribe(ActivityType.START_GAME, b);

        GameEvent event1 = new GameEvent("CASE1", 1, ActivityType.START_GAME, LocalDateTime.now(), "cat", 0, null,
                null, 0);
        manager.notify(ActivityType.START_GAME, event1);

        assertEquals(1, receivedA.size()); //notification check
        assertEquals(1, receivedB.size());

        manager.unsubscribe(ActivityType.START_GAME, b);

        GameEvent event2 = new GameEvent("CASE2", 2, ActivityType.START_GAME, LocalDateTime.now(), "cat2", 0,
                null, null, 0);
        manager.notify(ActivityType.START_GAME, event2);

        assertEquals(2, receivedA.size());
        assertEquals(1, receivedB.size()); // b shouldnt receive the second event
    }

    @Test
    void testSubscribeToUninitializedType() {
        EventManager manager = new EventManager(); // no initial operations
        List<GameEvent> rec = new ArrayList<>();
        Listener l = e -> rec.add(e);

        manager.subscribe(ActivityType.ANSWER_QUESTION, l);

        GameEvent ev = new GameEvent("CASEX", 3, ActivityType.ANSWER_QUESTION, LocalDateTime.now(), "catX", 100,
                "A", "Correct", 100);
        manager.notify(ActivityType.ANSWER_QUESTION, ev);

        assertEquals(1, rec.size());
        assertSame(ev, rec.get(0)); //event check
    }
}
