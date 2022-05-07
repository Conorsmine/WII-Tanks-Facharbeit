package com.Conorsmine.net.EventSystem.EventsManager;

public abstract class Event {

    private boolean canceled = false;
    private final String eventName;

    public Event(String eventName) {
        this.eventName = eventName;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getEventName() {
        return eventName;
    }

    public abstract void onCall(Event ev);
}
