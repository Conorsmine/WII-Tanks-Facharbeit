package com.Conorsmine.net.Game;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.Entities.TestEntity;
import com.Conorsmine.net.EventSystem.Events.GameShuttdownEvent;
import com.Conorsmine.net.EventSystem.Events.GameTickEvent;
import com.Conorsmine.net.EventSystem.Events.RenderTickEvent;
import com.Conorsmine.net.EventSystem.EventsManager.EventManager;
import com.Conorsmine.net.Rendering.*;
import com.Conorsmine.net.Rendering.Fonts.fontMeshCreator.FontType;
import com.Conorsmine.net.Rendering.Fonts.fontMeshCreator.GUIText;
import com.Conorsmine.net.Rendering.Fonts.fontRendering.TextMaster;
import com.Conorsmine.net.Rendering.Guis.GuiRenderer;
import com.Conorsmine.net.Rendering.Guis.GuiTexture;
import com.Conorsmine.net.Rendering.Models.TexturedModel;
import com.Conorsmine.net.Rendering.Textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static Game GAME;
    public static final int TPS = 60, FPS = 30;    // Both are in tps (ticks per second)
    private final ModelLoader loader = new ModelLoader();
    private RenderManager renderer;
    private final List<GuiTexture> guiList = new ArrayList<>();
    private GuiRenderer guiRenderer;
    private boolean running = false;
    private Thread gameThread;


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
        try {
            this.gameThread.interrupt();
            this.gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public static TestEntity turret;
    private void startRenderThread() {
        this.renderer = new RenderManager();
        this.guiRenderer = new GuiRenderer(this.loader);
        Camera camera = new Camera();
        TextMaster.init(this.loader);
        Light sun = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));

        camera.setPosition(new Vector3f(0, 3, 8));
        camera.setPitch(20);

        GameObjects tankBody = new TestEntity(new TexturedModel(ObjLoader.loadObjModel("tank", this.loader), new ModelTexture(this.loader.loadTexture("tankTextureV2"))));
//        turret = new TestEntity(new TexturedModel(ObjLoader.loadObjModel("tankTurretV1", this.loader), new ModelTexture(this.loader.loadTexture("amethyst_block"))));

        tankBody.setRotation(new Vector3f(0, 180, 0));
//        turret.setRotation(new Vector3f(0, 200, 0));
        tankBody.setScale(0.5f);
//        turret.setScale(0.5f);


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
                System.out.printf("FPS: %s\n", FPS / delta);
                // Do stuff here, as it should run with the given GAME_TICK_SPEED
                EventManager.callEvent(new RenderTickEvent(tick++, camera, sun));
                delta--;
            }
        }

        TextMaster.cleanUp();
        this.guiRenderer.cleanUp();
        this.renderer.cleanUp();
        this.loader.cleanUp();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ModelLoader getLoader() {
        return loader;
    }

    public RenderManager getRenderer() {
        return renderer;
    }

    public GuiRenderer getGuiRenderer() {
        return guiRenderer;
    }

    public List<GuiTexture> getGuis() {
        return this.guiList;
    }
}