package com.Conorsmine.net.Entities;

import com.Conorsmine.net.Rendering.Models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.HashMap;
import java.util.UUID;

public abstract class GameObjects implements Tickable {

    public static final HashMap<UUID, GameObjects> objectMap = new HashMap<>();

    private final UUID id;
    private TexturedModel model;
    private Vector3f position = new Vector3f(0, 0, 0);  // positional offset using a vector
    private Vector3f rotation = new Vector3f(0, 0, 0);  // rotational offset
    private float scale = 1;


    public GameObjects(UUID id, TexturedModel model) {
        this.id = id;
        this.model = model;
        objectMap.put(id, this);
    }

    public GameObjects(TexturedModel model) {
        this(UUID.randomUUID(), model);
    }

    public void destroy() {
        objectMap.remove(this.id);
    }



    public void addPosition(Vector3f offset) {
        this.position.x += offset.x;
        this.position.y += offset.y;
        this.position.z += offset.z;
    }

    public void addRotation(Vector3f offset) {
        this.rotation.x += offset.x;
        this.rotation.y += offset.y;
        this.rotation.z += offset.z;
    }

    public UUID getId() {
        return id;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
