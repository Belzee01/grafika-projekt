package engine.render;

import engine.mathutils.Maths;
import engine.model.RawModel;
import engine.model.textures.ModelTexture;
import engine.shaders.TerrainShader;
import engine.terrain.Terrain;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class TerrainRender {

    private TerrainShader terrainShader;

    public TerrainRender(TerrainShader terrainShader, Matrix4f projectionMatrix) {
        this.terrainShader = terrainShader;
        this.terrainShader.start();
        this.terrainShader.loadProjectionMatrix(projectionMatrix);
        this.terrainShader.stop();
    }

    public void render(List<Terrain> terrains) {
        terrains.forEach(t -> {
            prepareTerrainModel(t);
            loadModelMatrix(t);
            GL11.glDrawElements(GL11.GL_TRIANGLES, t.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        });
    }

    private void prepareTerrainModel(Terrain model) {
        RawModel rawModel = model.getModel();
        GL30.glBindVertexArray(rawModel.getVaoId());

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        ModelTexture texture = model.getTexture();
        terrainShader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureId());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector3f(terrain.getX(), 0, terrain.getZ()),
                new Vector3f(0, 0, 0),
                1);
        terrainShader.loadTransformationMatrix(transformationMatrix);
    }
}
