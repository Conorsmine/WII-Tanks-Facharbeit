package com.Conorsmine.net.Game;

import com.Conorsmine.net.EventSystem.Events.GameTickEvent;
import com.Conorsmine.net.EventSystem.Events.RenderTickEvent;
import com.Conorsmine.net.EventSystem.EventsManager.EventManager;

public class Game {

    public static Game GAME;
    public static final int TICK_SPEED = 60;    // In tps (ticks per second)
    private boolean running = false;
    private GameWindow frame;


    public Game() {
        if (GAME == null) {
            GAME = this;
            running = true;
            setup();
        }
    }

    private void setup() {
        new EventManager();
        this.frame = new GameWindow("WII Tanks", 889, 500);
        startGame();
    }

    private void cleanup() {

    }

    private void startGame() {
        double gameTickSpeed = 1000000000 / TICK_SPEED;
        double delta = 0;
        long lastUpdateTime = System.nanoTime();
        long currentTime;
        long tick = 0;

        while (running) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastUpdateTime) / gameTickSpeed;
            lastUpdateTime = currentTime;

            if (delta >= 1) {
                EventManager.callEvent(new GameTickEvent(tick));
                EventManager.callEvent(new RenderTickEvent(tick, Game.GAME.getFrame().getGraphics()));
                tick++;

                delta--;
            }
        }
    }

    public GameWindow getFrame() {
        return frame;
    }
}
