package com.Conorsmine.net.Entities.Walls;

import org.lwjgl.util.vector.Vector2f;

public class Line {

    private final Vector2f pointA, pointB, normal;

    public Line(Vector2f pointA, Vector2f pointB, Vector2f normal) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.normal = normal;
    }

    public float[] getTransformLine() {
        return new float[] {
                pointA.y - pointB.y,
                pointB.x - pointA.x,
                -(pointA.x * pointB.y - pointB.x * pointA.y)
        };
    }

    public Vector2f getPointA() {
        return pointA;
    }

    public Vector2f getPointB() {
        return pointB;
    }

    public Vector2f getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return String.format("Line[Vector2f[%s, %s], Vector2f[%s, %s]]", pointA.x, pointA.y, pointB.x, pointB.y);
    }
}
