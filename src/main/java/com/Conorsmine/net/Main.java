package com.Conorsmine.net;

import com.Conorsmine.net.Entities.Walls.Line;
import com.Conorsmine.net.Utils.Maths;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final List<Line> lineList = new ArrayList<>();

    public static void main(String[] args) {
//        new Game();
        long milli = System.currentTimeMillis();

        // 4 main walls
        lineList.add(new Line(new Vector2f(0, 14), new Vector2f(14, 14), new Vector2f(0, -1)));     // N
        lineList.add(new Line(new Vector2f(0, 0), new Vector2f(14, 0), new Vector2f(0, 1)));        // S
        lineList.add(new Line(new Vector2f(14, 0), new Vector2f(14, 14), new Vector2f(-1, 0)));     // E
        lineList.add(new Line(new Vector2f(0, 0), new Vector2f(0, 14), new Vector2f(1, 0)));        // W

        // W1
        lineList.add(new Line(new Vector2f(0, 12), new Vector2f(5, 12), new Vector2f(0, 1)));
        lineList.add(new Line(new Vector2f(0, 10), new Vector2f(5, 10), new Vector2f(0, -1)));
        lineList.add(new Line(new Vector2f(5, 10), new Vector2f(5, 12), new Vector2f(1, 0)));

        // W2
        lineList.add(new Line(new Vector2f(0, 7), new Vector2f(6, 7), new Vector2f(0, 1)));
        lineList.add(new Line(new Vector2f(0, 5), new Vector2f(6, 5), new Vector2f(0, -1)));
        lineList.add(new Line(new Vector2f(6, 5), new Vector2f(6, 7), new Vector2f(1, 0)));

        // W3
        lineList.add(new Line(new Vector2f(11, 7), new Vector2f(14, 7), new Vector2f(0, 1)));
        lineList.add(new Line(new Vector2f(11, 5), new Vector2f(14, 5), new Vector2f(0, -1)));
        lineList.add(new Line(new Vector2f(11, 5), new Vector2f(11, 7), new Vector2f(-1, 0)));

        // W4
        lineList.add(new Line(new Vector2f(7, 0), new Vector2f(7, 4), new Vector2f(-1, 0)));
        lineList.add(new Line(new Vector2f(9, 0), new Vector2f(9, 4), new Vector2f(1, 0)));
        lineList.add(new Line(new Vector2f(7, 4), new Vector2f(9, 4), new Vector2f(0, -1)));

        // Tanks
        Vector2f player = new Vector2f(5, 8);
        Vector2f tankT1 = new Vector2f(2, 13);  // (3, 1)
        Vector2f tankT2 = new Vector2f(2, 9);   // (-3, 1)  ✔
        Vector2f tankT3 = new Vector2f(5, 4);   // (3, -1)  ✔
        Vector2f tankT4 = new Vector2f(11, 3);  // (6, -5)  ✔


        Vector2f vec = findTargetTrajectory(player, tankT1, 1, 2, 1);
        System.out.println(vec);


        System.out.println("The code took: " + (System.currentTimeMillis() - milli) + " milli seconds.");
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
        if (canHit(origin, target)) return new Vector2f(target.x - origin.x, target.y - origin.y);
        rotOffset = (float) Math.max(rotOffset, 0.2);

        // check radially for bullet trajectory
        for (double rot = 0; rot < 360; rot += rotOffset) {
            Vector2f dir = Maths.radiansToVector(Math.toRadians(rot));

            // Loop for each bounce and check if the bullet will hit within the margin
            for (int i = 0; i < bounces; i++) {
                BulletHit wallHit = findIntersectionWithClosestLine(origin, dir);
                if (wallHit == null) continue;

                // Reflect of the wall
                Vector2f hit = wallHit.getHit();
                Line wall = wallHit.getWall();
                Vector2f reflection = Maths.reflect(dir, wall.getNormal());
                Line reflectedLine = new Line(hit, Maths.pointOnVectorLine(hit, reflection, 40000), null);

                // Create HelpLine to check for the distance between the target
                Vector2f targetLineNormal = Maths.orthotoganlLine(reflection);
                Line helpLine = new Line(
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

    private static BulletHit findIntersectionWithClosestLine(Vector2f origin, Vector2f dir) {
        double distance = Float.POSITIVE_INFINITY;
        Vector2f out = null;
        Line wall = null;

        for (Line line : lineList) {
            Vector2f hit = Maths.lineIntersection(
                    new Line(origin, Maths.pointOnVectorLine(origin, dir, 40000), null),
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
        for (Line line : lineList) {
            Vector2f hit = Maths.lineIntersection(
                    new Line(origin, target, null),
                    line
            );

            if (hit != null) return false;
        }

        return true;
    }

    public static class BulletHit {
        private final Vector2f hit;
        private final Line wall;

        public BulletHit(Vector2f hit, Line wall) {
            this.hit = hit;
            this.wall = wall;
        }

        public Vector2f getHit() {
            return hit;
        }

        public Line getWall() {
            return wall;
        }

        @Override
        public String toString() {
            return String.format("BulletHit[Vector2f[%s, %s], {%s}]", this.hit.getX(), this.hit.getY(), this.wall.toString());
        }
    }
}
