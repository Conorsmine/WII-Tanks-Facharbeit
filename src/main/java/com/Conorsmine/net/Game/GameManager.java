package com.Conorsmine.net.Game;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.EventSystem.Events.GameTickEvent;
import com.Conorsmine.net.EventSystem.EventsManager.EventExecutor;
import com.Conorsmine.net.EventSystem.EventsManager.EventPriority;
import com.Conorsmine.net.EventSystem.EventsManager.Listener;

import java.util.HashMap;

public class GameManager implements Listener {

    @EventExecutor ( priority = EventPriority.HIGHEST )
    public static void onGameTick(GameTickEvent ev) {
        // tick all entities
        new HashMap<>(GameObjects.objectMap).forEach((id, obj) -> {
            obj.tick();
        });
    }
}
