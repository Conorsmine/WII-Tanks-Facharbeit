package com.Conorsmine.net.Rendering.Fonts.fontRendering;


import com.Conorsmine.net.Rendering.Shaders.ShaderProgram;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class FontShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/main/java/com/Conorsmine/net/Rendering/Fonts/fontRendering/fontVertex";
	private static final String FRAGMENT_FILE = "src/main/java/com/Conorsmine/net/Rendering/Fonts/fontRendering/fontFragment";

	private int location_colour, location_translation, location_outlineColour, location_shouldRender;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		this.location_colour = super.getUniformLocation("colour");
		this.location_translation = super.getUniformLocation("translation");
		this.location_outlineColour = super.getUniformLocation("outlineColour");
		this.location_shouldRender = super.getUniformLocation("shouldRender");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	protected void loadColour(Vector3f colour) {
		super.loadVec(this.location_colour, colour);
	}

	protected void loadTranslation(Vector2f translation) {
		super.loadVec(this.location_translation, translation);
	}

	protected void loadOutlineColour(Vector3f colour) {
		super.loadVec(this.location_outlineColour, colour);
	}

	protected void loadShouldRender(boolean render) {
		super.loadBoolean(this.location_shouldRender, render);
	}


}
