package com.Conorsmine.net.Entities.Interfaces;

import com.Conorsmine.net.Game.EventsManager.EventManager;

public interface Breakable {

    boolean bBreak = false;

    default void onBreak() {

    }
}
