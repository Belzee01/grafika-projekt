package engine.render;

import engine.entities.Entity;
import engine.mathutils.Maths;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.model.textures.ModelTexture;
import engine.shaders.ShaderLoader;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

public class EntityRender {

    private ShaderLoader shader;

    public EntityRender(ShaderLoader shader,Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entities) {
        for (TexturedModel model : entities.keySet()) {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for (Entity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(),
                        GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoId());

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        ModelTexture texture = model.getTexture();
        if (texture.getHasTransparency())
            MainRender.disableCulling();
        shader.loadFakeLightingVariable(texture.getFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureId());
    }

    private void unbindTexturedModel() {
        MainRender.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
                entity.getRotation(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
