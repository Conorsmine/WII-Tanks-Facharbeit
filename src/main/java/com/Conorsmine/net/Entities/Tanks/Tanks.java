package com.Conorsmine.net.Entities.Tanks;

import com.Conorsmine.net.Entities.GameObjects;
import com.sun.javafx.geom.Vec2d;

import java.util.UUID;

public abstract class Tanks extends GameObjects {

    private Vec2d turretRot = new Vec2d(0, 0);

    public Tanks(UUID id) {
        super(id);
    }

    public Tanks() {
    }

    public Vec2d getTurretRot() {
        return turretRot;
    }

    public void setTurretRot(Vec2d turretRot) {
        this.turretRot = turretRot;
    }
}
