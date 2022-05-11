package com.Conorsmine.net.EventSystem.Events;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.EventSystem.EventsManager.Event;
import com.Conorsmine.net.Game.Game;
import com.Conorsmine.net.Rendering.*;
import com.Conorsmine.net.Rendering.Fonts.fontRendering.TextMaster;
import com.Conorsmine.net.Rendering.Guis.GuiRenderer;
import com.Conorsmine.net.Rendering.Shaders.ShaderProgram;
import com.Conorsmine.net.Rendering.Shaders.StaticShader;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class RenderTickEvent extends Event {

    private final long tick;
    private final Camera camera;
    private final Light light;

    public RenderTickEvent(long tick, Camera camera, Light light) {
        super(RenderTickEvent.class.getSimpleName());
        this.tick = tick;
        this.camera = camera;
        this.light = light;
    }

    @Override
    public void onCall(Event ev) {
        RenderManager renderManager = Game.GAME.getRenderer();
        renderManager.render(this.light, this.camera);
        Game.GAME.getGuiRenderer().render(Game.GAME.getGuis());
        new HashMap<UUID, GameObjects>(GameObjects.objectMap).forEach((id, obj) -> {
            renderManager.processEntity(obj);
        });
        TextMaster.render();
        DisplayManager.updateDisplay();
    }

    public long getTick() {
        return tick;
    }

    public Camera getCamera() {
        return camera;
    }

    public Light getLight() {
        return light;
    }
}