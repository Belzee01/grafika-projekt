package engine.runner;

import engine.entities.Camera;
import engine.entities.Entity;
import engine.entities.Light;
import engine.model.RawModel;
import engine.model.TexturedModel;
import engine.model.loaders.Loader;
import engine.model.loaders.OBJLoader;
import engine.model.textures.ModelTexture;
import engine.render.DisplayManager;
import engine.render.MainRender;
import engine.terrain.Terrain;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("tree", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            entities.add(
                    new Entity(
                            staticModel,
                            new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600),
                            new Vector3f(0f, 0f, 0f),
                            3
                    )
            );
        }

        Light light = new Light(new Vector3f(20000, 20000, 2000), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("grass")));

        Camera camera = new Camera();
        MainRender renderer = new MainRender();

        while (!Display.isCloseRequested()) {
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            for (Entity entity : entities) {
                renderer.processEntity(entity);
            }

            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUpMemory();
        DisplayManager.closeDisplay();

    }
}
