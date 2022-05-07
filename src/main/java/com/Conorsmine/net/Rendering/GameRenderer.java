package com.Conorsmine.net.Rendering;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.EventSystem.Events.RenderTickEvent;
import com.Conorsmine.net.EventSystem.EventsManager.EventExecutor;
import com.Conorsmine.net.EventSystem.EventsManager.EventPriority;
import com.Conorsmine.net.EventSystem.EventsManager.Listener;
import com.Conorsmine.net.Game.Game;

import java.awt.*;
import java.util.HashMap;

public class GameRenderer implements Listener {

    public static Graphics GRAPHICS;

    public GameRenderer(Graphics g) {
        GRAPHICS = g;
    }

}
