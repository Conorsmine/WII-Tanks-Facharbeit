package com.Conorsmine.net.Game.EventsManager;

public class EventManager {

    public static final EventManager INSTANCE = new EventManager();

    public EventManager() {
        if (INSTANCE != null) new ListenerManager();
        else return;
        System.out.println(ListenerManager.INSTANCE.getListenerList().get(0).getEventMethods());
    }

    public void callEvent(Event ev) {
        for (RegisteredListener listener : ListenerManager.INSTANCE.getListenerList()) {
            for (EventMethod evMethod : listener.getEventMethods()) {

                // Check if it's the correct event and then call method
                if (!ev.getClass().getName().equals(evMethod.getEventName())) continue;
                try { evMethod.getMethod().invoke(null, ev); }
                catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

}
