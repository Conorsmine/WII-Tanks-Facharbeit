package com.Conorsmine.net.Rendering.Shaders;

import com.Conorsmine.net.Rendering.Camera;
import com.Conorsmine.net.Rendering.Light;
import com.Conorsmine.net.Utils.Maths;
import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/java/com/Conorsmine/net/Rendering/Shaders/vertexShader";
    private static final String FRAGMENT_FILE = "src/main/java/com/Conorsmine/net/Rendering/Shaders/fragmentShader";

    private int location_transformationMatrix, location_projectionMatrix, location_viewMatrix;
    private int location_lightPosition, location_lightColor;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        this.location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        this.location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        this.location_viewMatrix = super.getUniformLocation("viewMatrix");
        this.location_lightPosition = super.getUniformLocation("lightPosition");
        this.location_lightColor = super.getUniformLocation("lightColour");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(this.location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(this.location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(this.location_viewMatrix, Maths.createViewMatrix(camera));
    }

    public void loadLight(Light light) {
        super.loadVec(this.location_lightPosition, light.getPosition());
        super.loadVec(this.location_lightColor, light.getColour());
    }
}
