package com.Conorsmine.net.Game;

import com.Conorsmine.net.EventSystem.Events.GameTickEvent;
import com.Conorsmine.net.EventSystem.EventsManager.EventExecutor;
import com.Conorsmine.net.EventSystem.EventsManager.Listener;

public class Listeners implements Listener {


    @EventExecutor
    public static void onGameTick(GameTickEvent ev) {
    }
}
