package com.Conorsmine.net.EventSystem.Events;

import com.Conorsmine.net.EventSystem.EventsManager.Event;

public class GameTickEvent extends Event {

    private final long tick;

    public GameTickEvent(long tick) {
        super(GameTickEvent.class.getSimpleName());
        this.tick = tick;
    }

    @Override
    public void onCall(Event ev) {
//        System.out.println("Game: " + ((GameTickEvent) ev).getTick());
    }

    public long getTick() {return tick;}
}
