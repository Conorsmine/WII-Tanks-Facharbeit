package com.Conorsmine.net.Game.EventsManager;

import java.lang.reflect.Method;

public class EventMethod {

    private final Method method;
    private final String eventName;

    public EventMethod(Method method, String eventName) {
        this.method = method;
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public Method getMethod() {
        return method;
    }
}
