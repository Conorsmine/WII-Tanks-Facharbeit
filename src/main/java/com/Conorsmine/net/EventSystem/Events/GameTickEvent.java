package com.Conorsmine.net.EventSystem.Events;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.EventSystem.EventsManager.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameTickEvent extends Event {

    private final long tick;

    public GameTickEvent(long tick) {
        super(GameTickEvent.class.getSimpleName());
        this.tick = tick;
    }

    @Override
    public void onCall(Event ev) {
        // tick all entities
        new HashMap<>(GameObjects.objectMap).forEach((id, obj) -> {
            obj.tick();
        });
    }

    public long getTick() {return tick;}
}
