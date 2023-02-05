package com.Conorsmine.net.EventSystem.EventsManager;

import java.lang.reflect.Method;

public class EventMethod {

    private final Method method;
    private final String eventClassName;
    private final EventPriority priority;
//    private final boolean cancelable;
//    private final boolean defaultCanceled;    // This value checks if an event has been manually marked as (un)cancelable, true if it hasn't been explicitly canceled.

    public EventMethod(Method method, String eventName, EventPriority priority/*, boolean cancelable, boolean defaultCanceled*/) {
        this.method = method;
        this.eventClassName = eventName;
        this.priority = priority;
//        this.cancelable = cancelable;
//        this.defaultCanceled = defaultCanceled;
    }

    public EventMethod(Method method, String eventName) {
        this(method, eventName, EventPriority.NORMAL/*, false, false*/);
    }

    public EventMethod(EventMethod method, EventPriority priority/*, boolean cancelable, boolean defaultCanceled*/) {
        this(method.getMethod(), method.getEventClassName(), priority/*, cancelable, defaultCanceled*/);
    }

    public String getEventClassName() {
        return eventClassName;
    }

    public Method getMethod() {
        return method;
    }

    public EventPriority getPriority() {
        return priority;
    }

//    public boolean isCancelable() {
//        return cancelable;
//    }
//
//    public boolean isDefaultCanceled() {
//        return defaultCanceled;
//    }
}
