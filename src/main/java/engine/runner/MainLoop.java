package engine.runner;

import engine.entities.Camera;
import engine.entities.Configs;
import engine.entities.Light;
import engine.model.loaders.Loader;
import engine.model.textures.ModelTexture;
import engine.render.DisplayManager;
import engine.render.MainRender;
import engine.terrain.*;
import org.lwjgl.opengl.Display;

public class MainLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        Light light = new Light(Configs.LIGHT_POS, Configs.LIGHT_COL);
        NoiseGenerator generator = new ImprovedPerlinNoiseGenerator(22325225);
        Terrain terrain = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")), generator);

        Camera camera = new Camera();
        MainRender renderer = new MainRender();

        while (!Display.isCloseRequested()) {
            camera.move();

            renderer.processTerrain(terrain);

            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUpMemory();
        DisplayManager.closeDisplay();

    }
}
