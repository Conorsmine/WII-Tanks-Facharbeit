package com.Conorsmine.net.Entities;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public abstract class GameObjects implements Tickable {

    public static final HashMap<UUID, GameObjects> objectMap = new HashMap<>();

    private final UUID id;
    private int x, y;
    private double rot = 0;     // in degrees
    private boolean render = true;

    public GameObjects(UUID id) {
        this.id = id;
        objectMap.put(id, this);
    }

    public GameObjects() {
        this(UUID.randomUUID());
    }

    public UUID getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getRot() {
        return rot;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRot(Vec2d dir) {
        if (dir.x == 0) {
            this.rot = 0;
            return;
        }
        this.rot = Math.toDegrees(Math.atan(dir.y / dir.x));
    }

    public void setRot(double rot) {
        this.rot = rot % 360;
    }

    public void addX(int xOffset) {
        this.x += xOffset;
    }

    public void addY(int yOffset) {
        this.y += yOffset;
    }

    public boolean isRender() {
        return render;
    }

    public void destroy() {
        this.render = false;
        objectMap.remove(this.id);
    }

    @Override
    public void renderTick(Graphics g) {
        if (this.render) this.render(((Graphics2D) g));
    }

    public abstract void render(Graphics2D g2d);

    public void shouldRender(boolean render) {
        this.render = render;
    }
}
