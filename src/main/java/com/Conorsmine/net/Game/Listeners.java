package com.Conorsmine.net.Game;

import com.Conorsmine.net.EventSystem.Events.GameTickEvent;
import com.Conorsmine.net.EventSystem.Events.RenderTickEvent;
import com.Conorsmine.net.EventSystem.EventsManager.EventExecutor;
import com.Conorsmine.net.EventSystem.EventsManager.Listener;
import org.lwjgl.util.vector.Vector3f;

public class Listeners implements Listener {


    @EventExecutor
    public static void onGameTick(GameTickEvent ev) {
//        if (Game.turret == null) return;
//        Game.turret.addRotation(new Vector3f(0, 0.4f, 0));
    }
}
