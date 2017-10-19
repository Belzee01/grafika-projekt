package engine.runner;

import engine.render.DisplayManager;
import org.lwjgl.opengl.Display;

public class MainLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        while(!Display.isCloseRequested()) {
            //game logic
            //render
            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();
    }

}
