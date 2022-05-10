package com.Conorsmine.net.EventSystem.Events;

import com.Conorsmine.net.EventSystem.EventsManager.Event;
import com.Conorsmine.net.Game.Game;
import com.Conorsmine.net.Rendering.DisplayManager;
import org.lwjgl.opengl.Display;

public class GameShuttdownEvent extends Event {

    public GameShuttdownEvent() {
        super(GameShuttdownEvent.class.getSimpleName());
    }

    @Override
    public void onCall(Event ev) {
        Game.GAME.setRunning(false);
        DisplayManager.closeDisplay();
        Game.GAME.cleanup();
    }
}
