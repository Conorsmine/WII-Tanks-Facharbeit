package com.Conorsmine.net.Rendering;

import com.Conorsmine.net.Game.Game;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager {

    public static final int WIDHT = 890, HEIGHT = 500;

    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3,2)
            .withForwardCompatible(true)
            .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDHT, HEIGHT));
            Display.setTitle("WII Tanks");
            Display.create(new PixelFormat(), attribs);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDHT, HEIGHT);
    }

    public static void closeDisplay() {
        Display.destroy();
    }

    public static void updateDisplay() {
        Display.sync(Game.FPS);
        Display.update();
    }
}
