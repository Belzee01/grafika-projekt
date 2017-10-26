package engine.runner;

import engine.entities.Entity;
import engine.model.TexturedModel;
import engine.model.loaders.Loader;
import engine.model.RawModel;
import engine.model.textures.ModelTexture;
import engine.render.DisplayManager;
import engine.render.Render;
import engine.shaders.ShaderLoader;
import engine.shaders.ShaderService;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class MainLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Render render = new Render();
        ShaderLoader shaderService = new ShaderLoader();

        float[] vertices = {
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
        };

        int[] indices = {
                0, 1, 3, 3, 1, 2
        };

        float[] textureCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("mur_tex.png"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(-1, 0, 0), new Vector3f(0, 0, 0), 1);

        while (!Display.isCloseRequested()) {
            entity.updatePosition(0.002f, 0.0f, 0.0f);
            entity.updateRotation(0, 1, 0);

            render.setUp();
            shaderService.start();

            render.render(entity, shaderService);

            shaderService.stop();
            DisplayManager.updateDisplay();
        }

        shaderService.cleanUp();
        loader.cleanUpMemory();
        DisplayManager.closeDisplay();
    }
}
