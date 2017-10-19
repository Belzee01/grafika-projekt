package engine.render;

import engine.entities.Entity;
import engine.mathutils.Maths;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.shaders.ShaderLoader;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

public class Render {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix = new Matrix4f();

    public void setUp() {
        GL11.glClearColor(1, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void render(Entity entity, ShaderLoader shaderLoader) {
        TexturedModel texturedModel = entity.getTexturedModel();
        RawModel rawModel = texturedModel.getRawModel();

        GL30.glBindVertexArray(rawModel.getVaoId());

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
        shaderLoader.loadTransformationMatrix(transformationMatrix);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);

        GL30.glBindVertexArray(0);
    }

    private void createProjectionMatrix() {
        float spectRation = (float) Display.getWidth() / (float) Display.getHeight();
        float yScale = 0;
    }
}
