package com.Conorsmine.net.Rendering;

import com.Conorsmine.net.Game.Game;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager {

    public static final int WIDTH = 1024, HEIGHT = 720;

    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3,2)
            .withForwardCompatible(true)
            .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setResizable(true);
            Display.setTitle("WII Tanks");
            Display.create(new PixelFormat(), attribs);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void closeDisplay() {
        Display.destroy();
    }

    public static void updateDisplay() {
        Display.sync(Game.FPS);
        Display.update();
    }
}
