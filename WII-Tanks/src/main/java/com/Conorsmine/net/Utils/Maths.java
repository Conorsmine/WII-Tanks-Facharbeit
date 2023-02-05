package com.Conorsmine.net.Utils;

import com.Conorsmine.net.Entities.Wall;
import com.Conorsmine.net.Rendering.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {

    public static Matrix4f createTransformationMatrix(Vector3f positionOffset, float rx, float ry, float rz, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity(); // Resets
        Matrix4f.translate(positionOffset, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }

    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.getX(), scale.getY(), 1f), matrix, matrix);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity(); // Resets
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), matrix, matrix);
        Vector3f negView = new Vector3f(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
        Matrix4f.translate(negView, matrix, matrix);
        return matrix;
    }

//    public static Vector2f lineIntersection(Wall line1, Wall line2) {
//        float[] transformLineA = line1.getTransformLine();
//        float[] transformLineB = line2.getTransformLine();
//
//        float matDet = transformLineA[0] * transformLineB[1] - transformLineA[1] * transformLineB[0];
//        float diffX = transformLineA[2] * transformLineB[1] - transformLineA[1] * transformLineB[2];
//        float diffY = transformLineA[0] * transformLineB[2] - transformLineA[2] * transformLineB[0];
//
//        if (matDet != 0) {
//            Vector2f point = new Vector2f((diffX / matDet), (diffY / matDet));
//
//            // Check if the point is within the bounds of the lines
//            if (!(isWithinRange(point, line1) && isWithinRange(point, line2))) return null;
//
//            return point;
//        }
//
//        return null;
//    }

    public static Vector2f radiansToVector(double radians) {
        return new Vector2f((float) Math.cos(radians), (float) Math.sin(radians));
    }

    public static double dotProduct(Vector2f vecA, Vector2f vecB) {
        return (vecA.getX() * vecB.getX()) + (vecA.getY() * vecB.getY());
    }

    public static Vector2f reflect(Vector2f dirIn, Vector2f normal) {
        normal = (Vector2f) normal.normalise();
        return new Vector2f(
                (float) (dirIn.getX() - 2 * Maths.dotProduct(dirIn, normal) * normal.getX()),
                (float) (dirIn.getY() - 2 * Maths.dotProduct(dirIn, normal) * normal.getY())
        );
    }

    public static Vector2f pointOnVectorLine(Vector2f origin, Vector2f dir, float move) {
        return new Vector2f((origin.getX() + dir.getX() * move), (origin.getY() + dir.getY() * move));
    }

    public static Vector2f orthotoganlLine(Vector2f dir) {
        // Skalarprodukt muss 0 sein
        // dir.x * out.x + dir.y * out.y = 0    | - dir.y * out.y
        // dir.x * out.x = - dir.y * out.y      | / (- dir.y)
        // (dir.x * out.x) / - dir.y = out.y    | / out.x
        // dir.x / - dir.y = out.y / out.x
        return new Vector2f(dir.getY(), -dir.getX());
    }

//    private static boolean isWithinRange(Vector2f point, Wall line) {
//        Vector2f pA = line.getPointA();
//        Vector2f pB = line.getPointB();
//
//        return Math.max(pA.y, pB.y) >= point.y && point.y >= Math.min(pA.y, pB.y)
//                && Math.max(pA.x, pB.x) >= point.x && point.x >= Math.min(pA.x, pB.x);
//    }

}
