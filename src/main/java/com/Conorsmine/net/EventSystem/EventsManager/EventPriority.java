package com.Conorsmine.net.EventSystem.EventsManager;

public enum EventPriority {

    HIGHEST(4),
    HIGH(3),
    NORMAL(2),
    LOW(1),
    LOWEST(0);

    final int priority;
    EventPriority(int prio) {
        this.priority = prio;
    }

    public int getVal() {
        return priority;
    }
}
