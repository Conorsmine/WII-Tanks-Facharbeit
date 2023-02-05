package com.Conorsmine.net.Entities;

import com.Conorsmine.net.Game.Game;
import com.Conorsmine.net.Rendering.Models.TexturedModel;
import com.Conorsmine.net.Rendering.ObjLoader;
import com.Conorsmine.net.Rendering.Textures.ModelTexture;
import org.lwjgl.util.vector.Vector2f;

public class Wall extends GameObjects {

//    private final Vector2f pointA, pointB, normal;

    public Wall(/*Vector2f pointA, Vector2f pointB, Vector2f normal*/) {
        super(new TexturedModel(ObjLoader.loadObjModel("simpleCube", Game.GAME.getLoader()), new ModelTexture(Game.GAME.getLoader().loadTexture("playerColor"))));
//        this.pointA = pointA;
//        this.pointB = pointB;
//        this.normal = normal;

    }

    public Wall(boolean s) {
        super(new TexturedModel(ObjLoader.loadObjModel("simpleCube", Game.GAME.getLoader()), new ModelTexture(Game.GAME.getLoader().loadTexture("breakableWall"))));
    }

//    public float[] getTransformLine() {
//        return new float[] {
//                pointA.y - pointB.y,
//                pointB.x - pointA.x,
//                -(pointA.x * pointB.y - pointB.x * pointA.y)
//        };
//    }

//    public Vector2f getPointA() {
//        return pointA;
//    }
//
//    public Vector2f getPointB() {
//        return pointB;
//    }
//
//    public Vector2f getNormal() {
//        return normal;
//    }

//    @Override
//    public String toString() {
//        return String.format("Line[Vector2f[%s, %s], Vector2f[%s, %s]]", pointA.x, pointA.y, pointB.x, pointB.y);
//    }

    @Override
    public void tick() {}
}
