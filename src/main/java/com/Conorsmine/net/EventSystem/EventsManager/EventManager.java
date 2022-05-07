package com.Conorsmine.net.EventSystem.EventsManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EventManager {

    public static EventManager INSTANCE;

    // Will store all EventMethods of a particular event, orders it after the set priority
    private final HashMap<String, List<EventMethod>> eventMap;

    public EventManager() {
        this.eventMap = new HashMap<>();
        if (INSTANCE == null) {
            INSTANCE = this;
            new ListenerManager();
        }
        else return;

        for (RegisteredListener listener : ListenerManager.INSTANCE.getListenerList()) {
            if (listener.getEventMethods().isEmpty()) continue;

            // Sort after event priority and add the list of EventMethods to the eventMap
            List<EventMethod> methodList = bubbleSort(listener.getEventMethods());
            this.eventMap.put(methodList.get(0).getEventClassName(), methodList);
        }
    }

    public static void callEvent(Event ev) {
        List<EventMethod> methodList = INSTANCE.eventMap.get(ev.getClass().getName());

        if (methodList == null) {
            if (!ev.isCanceled()) ev.onCall(ev);
            return;
        }
        for (EventMethod method : methodList) {
            try {
                method.getMethod().invoke(null, ev);

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Event function has to be static !");
            }
        }

        if (!ev.isCanceled()) ev.onCall(ev);
    }

    private List<EventMethod> bubbleSort(List<EventMethod> list) {
        List<EventMethod> out = new LinkedList<>(list);
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < out.size() - 1; i++) {
                EventMethod testFor = out.get(i);
                if (testFor.getPriority().getVal() <= out.get(i + 1).getPriority().getVal()) continue;

                out.set(i, out.get(i + 1));
                out.set(i + 1, testFor);
                swapped = true;
            }
        }
        return out;
    }

}
