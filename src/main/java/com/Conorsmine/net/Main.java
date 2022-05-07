package com.Conorsmine.net;

import com.Conorsmine.net.Entities.Tanks.PlayerTank;
import com.Conorsmine.net.EventSystem.EventsManager.EventManager;
import com.Conorsmine.net.Game.Game;

public class Main {

    public static void main(String[] args) {
        new PlayerTank();
        new EventManager();
        new Game();
    }
}
