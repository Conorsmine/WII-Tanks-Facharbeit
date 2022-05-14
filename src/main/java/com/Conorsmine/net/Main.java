package com.Conorsmine.net;

import com.Conorsmine.net.Entities.Wall;
import com.Conorsmine.net.Game.Game;
import com.Conorsmine.net.Utils.Maths;
import com.sun.javafx.geom.Vec4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final List<Wall> lineList = new ArrayList<>();

    public static void main(String[] args) {
//        new Game();
    }


    /**
     * @param origin The origin point.
     * @param target The target point.
     * @param rotOffset The stepsize for the rotation.
     * @param bounces The amount of reflections.
     * @param margin The margin of error to the target point.
     * @return Vector for hitting the target with the giving parameters.
     */
    private static Vector2f findTargetTrajectory(Vector2f origin, Vector2f target, float rotOffset, int bounces, float margin) {
        if (canHit(origin, target)) return new Vector2f(target.getX() - origin.getX(), target.getY() - origin.getY());

        Vector2f parCheck = parallelityCheck(origin, target);
        if (parCheck != null) return new Vector2f((parCheck.getX() - origin.getX()), (parCheck.getY() - origin.getY()));

        rotOffset = (float) Math.max(rotOffset, 0.005);

        // check radially for bullet trajectory
        for (double rot = 0; rot < 360; rot += rotOffset) {
            Vector2f dir = Maths.radiansToVector(Math.toRadians(rot));

            // Loop for each bounce and check if the bullet will hit within the margin
            for (int i = 0; i < bounces; i++) {

                BulletHit wallHit = findIntersectionWithClosestLine(origin, dir);
                if (wallHit == null) continue;

                // Reflect of the wall
                Vector2f hit = wallHit.getHit();
                Wall wall = wallHit.getWall();
                Vector2f reflection = Maths.reflect(dir, wall.getNormal());
                Wall reflectedLine = new Wall(hit, Maths.pointOnVectorLine(hit, reflection, 40000), null);

                // Create HelpLine to check for the distance between the target
                Vector2f targetLineNormal = Maths.orthotoganlLine(reflection);
                Wall helpLine = new Wall(
                        Maths.pointOnVectorLine(target, targetLineNormal, margin),
                        Maths.pointOnVectorLine(target, targetLineNormal, -margin),
                        null
                );

                // Intersection between the HelpLine and the reflected line
                Vector2f helpLineHit = Maths.lineIntersection(reflectedLine, helpLine);
                if (helpLineHit == null) continue;

                // Distance between the target point and the intersection of the reflected line with the HelpLine is smaller than the margin of error allowed
                if (!(new Vector2f((target.getX() - helpLineHit.getX()), (target.getY() - helpLineHit.getY())).length() <= margin)) continue;

                // The Point can be reached without going through a wall
                if (canHit(hit, helpLineHit)) return dir;
            }
        }


        return null;
    }

    // This function checks if the target can be hit via one reflection by checking if there is a reflection halfway between the origin and the target point.
    private static Vector2f parallelityCheck(Vector2f origin, Vector2f target) {
        Vector2f halfwayTransform = new Vector2f((target.getX() - origin.getX()) * 0.5f, (target.getY() - origin.getY()) * 0.5f);
        Vector2f halfwayOriginToTarget = new Vector2f((origin.getX() + halfwayTransform.getX()), (origin.getY() + halfwayTransform.getY()));
        Vector2f normal = (Vector2f) Maths.orthotoganlLine(halfwayTransform).normalise();
        Vector2f normalPointA = Maths.pointOnVectorLine(halfwayOriginToTarget, normal, 40000);
        Vector2f normalPointB = Maths.pointOnVectorLine(halfwayOriginToTarget, normal, -40000);
        normal = new Vector2f(Math.abs(normal.getX()), Math.abs(normal.getY()));

        for (Wall line : lineList) {
            Vector2f reflectionPoint = Maths.lineIntersection(new Wall(normalPointA, normalPointB, null), line);

            // Can hit the wall from the current location
            if (reflectionPoint == null) continue;

            // Opposite vector of the wall, should be identical to the normal vector
            Vector2f wallNormal = (Vector2f) new Vector2f(Math.abs(line.getNormal().getX()), Math.abs(line.getNormal().getY())).normalise();

            // Check if the wall allows for the correct reflection
            if (!wallNormal.equals(normal)) continue;

            // Checks if the reflectionPoint can be hit
            if (!canHit(origin, Maths.pointOnVectorLine(origin, new Vector2f((reflectionPoint.getX() - origin.getX()), (reflectionPoint.getY() - origin.getY())), 0.9f))) continue;

            // Wall could reflect correctly
            return reflectionPoint;
        }

        return null;
    }

    private static BulletHit findIntersectionWithClosestLine(Vector2f origin, Vector2f dir) {
        double distance = Float.POSITIVE_INFINITY;
        Vector2f out = null;
        Wall wall = null;

        for (Wall line : lineList) {
            Vector2f hit = Maths.lineIntersection(
                    new Wall(origin, Maths.pointOnVectorLine(origin, dir, 40000), null),
                    line
            );

            if (hit != null) {;
                if (hit.length() < distance) {
                    distance = hit.length();
                    wall = line;
                    out = hit;
                }
            }
        }
        if (out == null || wall == null) return null;

        return new BulletHit(out, wall);
    }

    // If the closest intersection with the wall is also needed use "findIntersectionWithClosestLine" and check if it's null, does the same.
    // But if you only need to check if there is a wall in the way then use this, as it is faster
    private static boolean canHit(Vector2f origin, Vector2f target) {
        for (Wall line : lineList) {
            Vector2f hit = Maths.lineIntersection(
                    new Wall(origin, target, null),
                    line
            );

            if (hit != null) return false;
        }

        return true;
    }

    public static class BulletHit {
        private final Vector2f hit;
        private final Wall wall;

        public BulletHit(Vector2f hit, Wall wall) {
            this.hit = hit;
            this.wall = wall;
        }

        public Vector2f getHit() {
            return hit;
        }

        public Wall getWall() {
            return wall;
        }

        @Override
        public String toString() {
            return String.format("BulletHit[Vector2f[%s, %s], {%s}]", this.hit.getX(), this.hit.getY(), this.wall.toString());
        }
    }
}
