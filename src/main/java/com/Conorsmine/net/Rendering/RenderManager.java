package com.Conorsmine.net.Rendering;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.Rendering.Models.TexturedModel;
import com.Conorsmine.net.Rendering.Shaders.StaticShader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RenderManager {

    private StaticShader shader = new StaticShader();
    private Renderer renderer = new Renderer(this.shader);

    private final HashMap<TexturedModel, List<GameObjects>> objectTextureMap = new HashMap<>();


    public void render(Light light, Camera camera) {
        this.renderer.prepare();
        this.shader.start();
        this.shader.loadLight(light);
        this.shader.loadViewMatrix(camera);
        this.renderer.render(this.objectTextureMap);
        this.shader.stop();
        this.objectTextureMap.clear();
    }

    public void processEntity(GameObjects obj) {
        List<GameObjects> batch = this.objectTextureMap.get(obj.getModel());
        if (batch != null) batch.add(obj);
        else {
            List<GameObjects> newBatch = new ArrayList<>();
            newBatch.add(obj);
            this.objectTextureMap.put(obj.getModel(), newBatch);
        }
    }

    public void cleanUp() {
        this.shader.cleanUp();
    }
}
