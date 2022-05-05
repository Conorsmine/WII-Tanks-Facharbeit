package com.Conorsmine.net;

import com.Conorsmine.net.Game.Events.CollideEvent;
import com.Conorsmine.net.Game.EventsManager.EventManager;

public class Main {

    public static void main(String[] args) {
        new EventManager();
        EventManager.INSTANCE.callEvent(new CollideEvent());
    }
}
