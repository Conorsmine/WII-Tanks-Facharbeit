package com.Conorsmine.net.Rendering;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.Rendering.Models.RawModel;
import com.Conorsmine.net.Rendering.Models.TexturedModel;
import com.Conorsmine.net.Rendering.Shaders.StaticShader;
import com.Conorsmine.net.Utils.Maths;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import java.util.HashMap;
import java.util.List;

public class Renderer {

    private static final float FOV = 70, NEAR_PLANE = 0.1f, FAR_PLANE = 1000;   // NEAR_PLANE, closest the "camera" can see, FAR_PLANE, furthest the "camera" can see

    private Matrix4f projectionMatrix;
    private final StaticShader shader;

    public Renderer(StaticShader shader) {
        this.shader = shader;
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(137 / 256, 1, 1, 1);
    }

    public void render(HashMap<TexturedModel, List<GameObjects>> objectTextureMap) {
        for (TexturedModel model : objectTextureMap.keySet()) {
            prepareTexturedModel(model);
            for (GameObjects obj : objectTextureMap.get(model)) {
                prepareInstance(obj);
            }

            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel texturedModel) {
        RawModel model = texturedModel.getModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
    }

    private void prepareInstance(GameObjects obj) {
        shader.loadTransformationMatrix(Maths.createTransformationMatrix(
                obj.getPosition(),
                obj.getRotation().x,
                obj.getRotation().y,
                obj.getRotation().z,
                obj.getScale()
        ));
        GL11.glDrawElements(GL11.GL_TRIANGLES, obj.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    // http://www.songho.ca/opengl/gl_projectionmatrix.html
    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1 / Math.tan(Math.toRadians(FOV / 2))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;  // visible area

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
}
