package engine.shaders;

import engine.entities.Camera;
import engine.entities.Light;
import engine.mathutils.Maths;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderLoader extends ShaderService {

    private static final String VERTEX_FILE = "C:\\Users\\Belzee\\IdeaProjects\\graphics-project\\src\\main\\resources\\shaders\\" + "vertexShader.txt";
    private static final String FRAGMENT_FILE = "C:\\Users\\Belzee\\IdeaProjects\\graphics-project\\src\\main\\resources\\shaders\\" + "fragmentShader.txt";

    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;

    public ShaderLoader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
    }

    public void loadLight(Light light){
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColour, light.getColour());
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }
}
