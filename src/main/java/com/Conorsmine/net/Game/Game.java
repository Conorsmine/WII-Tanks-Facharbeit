package com.Conorsmine.net.Game;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.Entities.TestEntity;
import com.Conorsmine.net.EventSystem.Events.GameShuttdownEvent;
import com.Conorsmine.net.EventSystem.Events.GameTickEvent;
import com.Conorsmine.net.EventSystem.Events.RenderTickEvent;
import com.Conorsmine.net.EventSystem.EventsManager.EventManager;
import com.Conorsmine.net.Rendering.*;
import com.Conorsmine.net.Rendering.Models.RawModel;
import com.Conorsmine.net.Rendering.Models.TexturedModel;
import com.Conorsmine.net.Rendering.Shaders.StaticShader;
import com.Conorsmine.net.Rendering.Textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Game {

    public static Game GAME;
    public static final int TPS = 60, FPS = 30;    // Both are in tps (ticks per second)
    private boolean running = false;
    private Thread gameThread;
    private Thread renderThread;


    public Game() {
        if (GAME == null) {
            GAME = this;
            running = true;
            new EventManager();
            setup();
        }
    }

    private void setup() {
        DisplayManager.createDisplay();
        startGameThread();
        startRenderThread();
    }

    public void cleanup() {
        this.gameThread.interrupt();
    }

    private void startGameThread() {
        this.gameThread = new Thread(new Runnable() {
            @Override
            public void run() {

                double gameTickSpeed = 1000000000 / TPS;
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
                        EventManager.callEvent(new GameTickEvent(tick++));
                        delta--;
                    }
                }
            }
        });
        this.gameThread.start();
    }

    public static GameObjects model;

    private void startRenderThread() {

        ModelLoader loader = new ModelLoader();
        RenderManager renderer = new RenderManager();
        Camera camera = new Camera();

        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        RawModel rawModel = ObjLoader.loadObjModel("stall", loader);
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));

        model = new TestEntity(new TexturedModel(rawModel, texture));

        double renderTickSpeed = 1000000000 / FPS;
        double delta = 0;
        long lastUpdateTime = System.nanoTime();
        long currentTime;
        long tick = 0;
        while (running) {
            if (Display.isCloseRequested()) {
                EventManager.callEvent(new GameShuttdownEvent());
                return;
            }

            currentTime = System.nanoTime();
            delta += (currentTime - lastUpdateTime) / renderTickSpeed;
            lastUpdateTime = currentTime;
            if (delta >= 1) {
                // Do stuff here, as it should run with the given GAME_TICK_SPEED
                EventManager.callEvent(new RenderTickEvent(tick++, renderer, camera, light));
                delta--;
            }
        }

        renderer.cleanUp();
        loader.cleanUp();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}