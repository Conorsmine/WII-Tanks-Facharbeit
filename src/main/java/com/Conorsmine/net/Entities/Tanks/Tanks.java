package com.Conorsmine.net.Entities.Tanks;

import com.Conorsmine.net.Entities.GameObjects;
import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.util.UUID;

public abstract class Tanks extends GameObjects {

    private double turretRot = 0;

    public Tanks(UUID id) {
        super(id);
    }

    public Tanks() {
    }

    public double getTurretRot() {
        return turretRot % 360;
    }

    public void setTurretRot(Vec2d dir) {
        if (dir.x == 0) {
            this.turretRot = 0;
            return;
        }
        this.turretRot = Math.toDegrees(Math.atan(dir.y / dir.x));
    }

    public void setTurretRot(double rot) {
        this.turretRot = rot % 360;
    }

    @Override
    public void render(Graphics2D g2d) {
        renderBody(g2d);
        renderTurret(g2d);
    }

    public abstract void renderBody(Graphics2D g2d);
    public abstract void renderTurret(Graphics2D g2d);
}
