package com.Conorsmine.net.EventSystem.Events;

import com.Conorsmine.net.EventSystem.EventsManager.Event;
import com.Conorsmine.net.Game.Game;

import java.awt.*;

public class RenderTickEvent extends Event {

    private final long tick;
    private final Graphics g;
    public RenderTickEvent(long tick, Graphics g) {
        super(RenderTickEvent.class.getSimpleName());
        this.tick = tick;
        this.g = g;
    }

    @Override
    public void onCall(Event ev) {
        Game.GAME.getFrame().drawCall(g);
        Game.GAME.getFrame().repaint();
    }

    public long getTick() {
        return tick;
    }

    public Graphics getGraphics() {
        return g;
    }
}