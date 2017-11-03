package engine.runner;

import engine.entities.Camera;
import engine.entities.Entity;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.model.loaders.Loader;
import engine.model.textures.ModelTexture;
import engine.render.DisplayManager;
import engine.model.loaders.OBJLoader;
import engine.render.Render;
import engine.shaders.ShaderLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class MainLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        ShaderLoader shaderService = new ShaderLoader();
        Render render = new Render(shaderService);

        RawModel model = OBJLoader.loadObjModel("C:\\Users\\Belzee\\IdeaProjects\\graphics-project\\src\\main\\resources\\models\\stall\\stall.obj", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("C:\\Users\\Belzee\\IdeaProjects\\graphics-project\\src\\main\\resources\\models\\stall\\stallTexture.png"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(-1, 0, -50), new Vector3f(0, 0, 0), 1);

        Camera camera = new Camera();

        while (!Display.isCloseRequested()) {
            entity.updateRotation(0.0f, 1f, .0f);

            camera.move();

            render.setUp();
            shaderService.start();

            shaderService.loadViewMatrix(camera);

            render.render(entity, shaderService);

            shaderService.stop();
            DisplayManager.updateDisplay();
        }

        shaderService.cleanUp();
        loader.cleanUpMemory();
        DisplayManager.closeDisplay();
    }
}
