package engine.runner;

import engine.model.TexturedModel;
import engine.model.loaders.Loader;
import engine.model.RawModel;
import engine.model.textures.ModelTexture;
import engine.render.DisplayManager;
import engine.render.Render;
import engine.shaders.ShaderLoader;
import engine.shaders.ShaderService;
import org.lwjgl.opengl.Display;

public class MainLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Render render = new Render();
        ShaderService shaderService = new ShaderLoader();

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
        ModelTexture texture = new ModelTexture(loader.loadTexture("C:\\Users\\Kajetan\\IntelliJProjects\\grafika-projekt\\src\\main\\resources\\mur_tex.png"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while (!Display.isCloseRequested()) {

            render.setUp();
            shaderService.start();

            render.render(texturedModel);

            shaderService.stop();
            DisplayManager.updateDisplay();
        }

        shaderService.cleanUp();
        loader.cleanUpMemory();
        DisplayManager.closeDisplay();
    }
}
