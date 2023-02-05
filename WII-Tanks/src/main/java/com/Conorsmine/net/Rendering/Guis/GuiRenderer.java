package com.Conorsmine.net.Rendering.Guis;

import com.Conorsmine.net.Rendering.ModelLoader;
import com.Conorsmine.net.Rendering.Models.RawModel;
import com.Conorsmine.net.Rendering.Shaders.GuiShader;
import com.Conorsmine.net.Utils.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class GuiRenderer {

    private final RawModel model;
    private final GuiShader shader;

    public GuiRenderer(ModelLoader loader) {
        this.model = loader.loadToVAO(new float[] { -1, 1, -1, -1, 1, 1, 1, -1 });
        this.shader = new GuiShader();
    }

    public void render(List<GuiTexture> guis) {
        this.shader.start();
        GL30.glBindVertexArray(this.model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        for (GuiTexture gui : guis) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
            this.shader.loadTransformation(Maths.createTransformationMatrix(gui.getPosition(), gui.getScale()));
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP,0, this.model.getVertexCount());
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        this.shader.stop();
    }



    public void cleanUp() {
        this.shader.cleanUp();
    }
}
