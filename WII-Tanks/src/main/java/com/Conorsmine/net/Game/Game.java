package com.Conorsmine.net.Game;

import com.Conorsmine.net.Entities.TestEntity;
import com.Conorsmine.net.Entities.Wall;
import com.Conorsmine.net.EventSystem.Events.GameShuttdownEvent;
import com.Conorsmine.net.EventSystem.Events.GameTickEvent;
import com.Conorsmine.net.EventSystem.Events.RenderTickEvent;
import com.Conorsmine.net.EventSystem.EventsManager.EventManager;
import com.Conorsmine.net.Levels.LevelManager;
import com.Conorsmine.net.Rendering.*;
import com.Conorsmine.net.Rendering.Fonts.fontRendering.TextMaster;
import com.Conorsmine.net.Rendering.Guis.GuiRenderer;
import com.Conorsmine.net.Rendering.Guis.GuiTexture;
import com.Conorsmine.net.Rendering.Models.TexturedModel;
import com.Conorsmine.net.Rendering.Textures.ModelTexture;
import com.Conorsmine.net.Utils.Maths;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static Game GAME;
    public static final int TPS = 20, FPS = 30;    // Both are in tps (ticks per second)
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

    private void startRenderThread() {
        this.renderer = new RenderManager();
        this.guiRenderer = new GuiRenderer(this.loader);
        Camera camera = new Camera();
        TextMaster.init(this.loader);
        Light sun = new Light(new Vector3f(8000, 7000, -5000), new Vector3f(1, 1, 1));

        // Loading lvl 1 as a demo site
        float scale = 3;

        TestEntity map = new TestEntity(new TexturedModel(ObjLoader.loadObjModel("map_Outer", this.loader), new ModelTexture(this.loader.loadTexture("wallColor")))); map.setRotation(new Vector3f(0, -90, 0)); map.setScale(scale);
        Wall wallA = new Wall(true); wallA.setScale(scale); wallA.setPosition(new Vector3f(2 * scale, 0, -0.5f * scale));
        Wall wallB = new Wall(true); wallB.setScale(scale); wallB.setPosition(new Vector3f(2 * scale, 0, 1.5f * scale));
        Wall wallC = new Wall(); wallC.setScale(scale); wallC.setPosition(new Vector3f(2 * scale, 0, -2.5f * scale));
        Wall wallD = new Wall(); wallD.setScale(scale); wallD.setPosition(new Vector3f(2 * scale, 0, 3.5f * scale));
        Wall wallE = new Wall(); wallE.setScale(scale); wallE.setPosition(new Vector3f(2 * scale, 0, -4.5f * scale));
        Wall wallF = new Wall(); wallF.setScale(scale); wallF.setPosition(new Vector3f(2 * scale, 0, 5.5f * scale));

        Wall wallG = new Wall(); wallG.setScale(scale); wallG.setPosition(new Vector3f(-8 * scale, 0, -2.5f * scale));
        Wall wallH = new Wall(); wallH.setScale(scale); wallH.setPosition(new Vector3f(-8 * scale, 0, 3.5f * scale));
        Wall wallI = new Wall(); wallI.setScale(scale); wallI.setPosition(new Vector3f(-8 * scale, 0, -4.5f * scale));
        Wall wallJ = new Wall(); wallJ.setScale(scale); wallJ.setPosition(new Vector3f(-8 * scale, 0, 5.5f * scale));

        TestEntity player = new TestEntity(new TexturedModel(ObjLoader.loadObjModel("tank", Game.GAME.getLoader()), new ModelTexture(Game.GAME.getLoader().loadTexture("playerColor"))));
        TestEntity enemy = new TestEntity(new TexturedModel(ObjLoader.loadObjModel("tank", Game.GAME.getLoader()), new ModelTexture(Game.GAME.getLoader().loadTexture("enemyTank"))));
        player.setPosition(new Vector3f(-14 * scale, 0, 0));
        enemy.setPosition(new Vector3f(14 * scale, 0, 0));
        enemy.setRotation(new Vector3f(0, 180, 0));
        player.setScale(scale / 2);
        enemy.setScale(scale / 2);



        // Viewing
        float yaw = 0;


        double renderTickSpeed = 1000000000 / (float)FPS;
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
//                System.out.printf("FPS: %s\n", FPS / delta);
                EventManager.callEvent(new RenderTickEvent(tick++, camera, sun));
                delta--;
            }


            if (Mouse.isButtonDown(1) && Mouse.isInsideWindow()) {
                yaw += Mouse.getDX() * 0.1;
                camera.setYaw(yaw - 90);

                Vector2f transform = Maths.radiansToVector(Math.toRadians(yaw % 360));
                camera.setPosition(new Vector3f(transform.getX() * 82, camera.getPosition().getY(), transform.getY() * 82));
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
                camera.setPosition(new Vector3f(0, 75, 82));
                camera.setPitch(53);
                camera.setYaw(0);
                yaw = 0;
            }
        }

        TextMaster.cleanUp();
        LevelManager.clearLevel();
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