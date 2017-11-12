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

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();

        RawModel fernModel = OBJLoader.loadObjModel("fern", loader);
        TexturedModel fernTexturedModel = new TexturedModel(fernModel, new ModelTexture(loader.loadTexture("fern")));
        for (int i = 0; i < 100; i++) {
            entities.add(new Entity(fernTexturedModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), new Vector3f(0f, 0f, 0f), 3));
        }

        RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
        TexturedModel grassTexturedModel = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("grassTexture")));
        for (int i = 0; i < 100; i++) {
            entities.add(new Entity(grassTexturedModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), new Vector3f(0f, 0f, 0f), 3));
        }

        RawModel lowPolyTreeModel = OBJLoader.loadObjModel("lowPolyTree", loader);
        TexturedModel lowPolyTreeTexturedModel = new TexturedModel(lowPolyTreeModel, new ModelTexture(loader.loadTexture("lowPolyTree")));
        for (int i = 0; i < 100; i++) {
            entities.add(new Entity(lowPolyTreeTexturedModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), new Vector3f(0f, 0f, 0f), 3));
        }

        Light light = new Light(new Vector3f(20000, 20000, 2000), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));

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
