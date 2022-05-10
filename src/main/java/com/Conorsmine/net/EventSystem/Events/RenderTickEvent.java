package com.Conorsmine.net.EventSystem.Events;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.EventSystem.EventsManager.Event;
import com.Conorsmine.net.Game.Game;
import com.Conorsmine.net.Rendering.*;
import com.Conorsmine.net.Rendering.Shaders.ShaderProgram;
import com.Conorsmine.net.Rendering.Shaders.StaticShader;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class RenderTickEvent extends Event {

    private final long tick;
    private final RenderManager masterRenderer;
    private final Camera camera;
    private final Light light;

    public RenderTickEvent(long tick, RenderManager masterRenderer, Camera camera, Light light) {
        super(RenderTickEvent.class.getSimpleName());
        this.tick = tick;
        this.masterRenderer = masterRenderer;
        this.camera = camera;
        this.light = light;
    }

    @Override
    public void onCall(Event ev) {
        this.masterRenderer.render(this.light, this.camera);
        new HashMap<UUID, GameObjects>(GameObjects.objectMap).forEach((id, obj) -> {
            this.masterRenderer.processEntity(obj);
        });
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