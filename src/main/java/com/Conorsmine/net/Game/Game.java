package com.Conorsmine.net.Game;

import com.Conorsmine.net.EventSystem.Events.GameTickEvent;
import com.Conorsmine.net.EventSystem.Events.RenderTickEvent;
import com.Conorsmine.net.EventSystem.EventsManager.EventManager;

public class Game {

    public static Game GAME;
    public static final int GAME_TICK_SPEED = 60, RENDER_TICK_SPEED = 30;    // Both are in tps (ticks per second)
    private boolean running = false;
    private GameWindow frame;
    private Thread gameThread;
    private Thread renderThread;


    public Game() {
        if (GAME == null){
            GAME = this;
            running = true;
            new EventManager();
            setup();
        }
    }

    private void setup() {
        this.frame = new GameWindow("WII Tanks", 890, 500);
        startGameThread();
        startRenderThread();
    }

    private void cleanup() {
        this.gameThread.interrupt();
        this.renderThread.interrupt();
    }

    private void startGameThread() {
        this.gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                double gameTickSpeed = 1000000000 / GAME_TICK_SPEED;
                double delta = 0;
                long lastUpdateTime = System.nanoTime();
                long currentTime;
                long tick = 0;
                while (running) {
                    currentTime = System.nanoTime();
                    delta += (currentTime - lastUpdateTime) / gameTickSpeed;
                    lastUpdateTime = currentTime;
                    if (delta >= 1) {
                        // Do stuff here, as it should run with the given GAME_TICK_SPEED
                        EventManager.callEvent(new GameTickEvent(tick));
                        tick++;
                        delta--;
                    }
                }
            }
        });
        this.gameThread.start();
    }

    private void startRenderThread() {
        this.renderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                double renderTickSpeed = 1000000000 / RENDER_TICK_SPEED;
                double delta = 0;
                long lastUpdateTime = System.nanoTime();
                long currentTime;
                long tick = 0;
                while (running) {
                    currentTime = System.nanoTime();
                    delta += (currentTime - lastUpdateTime) / renderTickSpeed;
                    lastUpdateTime = currentTime;
                    if (delta >= 1) {
                        // Do stuff here, as it should run with the given GAME_TICK_SPEED
                        EventManager.callEvent(new RenderTickEvent(tick, Game.GAME.getFrame().getGraphics()));
                        tick++;
                        delta--;
                    }
                }
            }
        });
        this.renderThread.start();
    }

    public GameWindow getFrame() {
        return frame;
    }
}