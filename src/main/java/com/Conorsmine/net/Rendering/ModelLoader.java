package com.Conorsmine.net.Rendering;

import com.Conorsmine.net.Rendering.Models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, positions, 3);
        storeDataInAttributeList(1, textureCoords, 2);
        storeDataInAttributeList(2, normals, 3);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public RawModel loadToVAO(float[] positions) {
        int vaoID = createVAO();
        this.storeDataInAttributeList(0, positions, 2);
        unbindVAO();
        return new RawModel(vaoID, positions.length / 2);
    }

    public int loadToVAO(float[] positions, float[] textureCoords) {
        int vaoID = createVAO();
        storeDataInAttributeList(0, positions, 2);
        storeDataInAttributeList(1, textureCoords, 2);
        unbindVAO();
        return vaoID;
    }

    public int loadTexture(String textureame) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("src/main/resources/assets/" + textureame + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Image could not be loaded, make sure the image is of type PNG and has a size of x^2 !");
        }

        int textureID = texture.getTextureID();
        textures.add(textureID);

        return textureID;
    }

    public int loadFontImage(String textureame) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("src/main/resources/Fonts/" + textureame + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Image could not be loaded, make sure the image is of type PNG and has a size of x^2 !");
        }

        int textureID = texture.getTextureID();
        textures.add(textureID);

        return textureID;
    }

    public File loadFont(String fontName) {
        return new File("src/main/resources/Fonts/" + fontName + ".fnt");
    }

    private int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attribute, float[] data, int size) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = convertToFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STREAM_DRAW);
        GL20.glVertexAttribPointer(attribute, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private FloatBuffer convertToFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = convertToIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer convertToIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void cleanUp() {
        for (int vao : vaos) GL30.glDeleteVertexArrays(vao);
        for (int vbo : vbos) GL30.glDeleteVertexArrays(vbo);
        for (int texture : textures) GL11.glDeleteTextures(texture);
    }
}
