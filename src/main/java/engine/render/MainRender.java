package engine.render;

import engine.entities.Camera;
import engine.entities.Entity;
import engine.entities.Light;
import engine.model.TexturedModel;
import engine.shaders.ShaderLoader;
import engine.terrain.Terrain;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainRender {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;

    private ShaderLoader shader = new ShaderLoader();
    private EntityRender renderer;

//    private TerrainRenderer terrainRenderer;
//    private TerrainShader terrainShader = new TerrainShader();


    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<Terrain>();

    public MainRender() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        renderer = new EntityRender(shader, projectionMatrix);
//        terrainRenderer = new TerrainRenderer(terrainShader,projectionMatrix);
    }

    public void render(Light sun, Camera camera) {
        setUp();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
//        terrainShader.start();
//        terrainShader.loadLight(sun);
//        terrainShader.loadViewMatrix(camera);
//        terrainRenderer.render(terrains);
//        terrainShader.stop();
        terrains.clear();
        entities.clear();
    }

    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    public void setUp() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.49f, 89f, 0.98f, 1);
    }

    public void processEntity(Entity entity) {
        TexturedModel entityModel = entity.getTexturedModel();
        List<Entity> batch = entities.get(entityModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void cleanUp() {
        shader.cleanUp();
//        terrainShader.cleanUp();
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

}