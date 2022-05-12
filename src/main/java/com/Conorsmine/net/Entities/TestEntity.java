package com.Conorsmine.net.Entities;

import com.Conorsmine.net.Rendering.Models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class TestEntity extends GameObjects {

    public TestEntity(TexturedModel model) {
        super(model);
        this.setPosition(new Vector3f(0, 0, -8));
    }

    @Override
    public void tick() {
        this.addRotation(new Vector3f(0, 0.2f, 0));
    }
}
