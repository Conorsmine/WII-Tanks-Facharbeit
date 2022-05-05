package com.Conorsmine.net.Game.EventsManager;

import java.util.List;

public class RegisteredListener {

    private final Class<? extends Listener> listener;
    private final List<EventMethod> eventMethods;

    public RegisteredListener(Class<? extends Listener> listenerClass, List<EventMethod> eventMethods) {
        this.listener = listenerClass;
        this.eventMethods = eventMethods;
    }

    public Class<? extends Listener> getListener() {
        return listener;
    }

    public List<EventMethod> getEventMethods() {
        return eventMethods;
    }
}
