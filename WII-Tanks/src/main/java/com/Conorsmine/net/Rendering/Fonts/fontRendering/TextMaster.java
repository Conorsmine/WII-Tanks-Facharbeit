package com.Conorsmine.net.Rendering.Fonts.fontRendering;

import com.Conorsmine.net.Rendering.Fonts.fontMeshCreator.FontType;
import com.Conorsmine.net.Rendering.Fonts.fontMeshCreator.GUIText;
import com.Conorsmine.net.Rendering.Fonts.fontMeshCreator.TextMeshData;
import com.Conorsmine.net.Rendering.ModelLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextMaster {

    private static ModelLoader loader;
    private static HashMap<FontType, List<GUIText>> texts = new HashMap<>();
    private static FontRenderer renderer;

    public static void init(ModelLoader iLoader) {
        renderer = new FontRenderer();
        loader = iLoader;
    }

    public static void render () {
        renderer.render(texts);
    }

    public static void loadText(GUIText text) {
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        List<GUIText> textBatch = texts.get(font);

        if (textBatch == null) {
            textBatch = new ArrayList<>();
            texts.put(font, textBatch);
        }

        textBatch.add(text);
    }

    public static void removeText(GUIText text) {
        List<GUIText> textBatch = texts.get(text.getFont());
        textBatch.remove(text);
        if (textBatch.isEmpty()) texts.remove(text.getFont());
    }

    public static void cleanUp() {
        renderer.cleanUp();
    }
}
