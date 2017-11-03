package engine.shaders;

import engine.entities.Camera;
import engine.mathutils.Maths;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderLoader extends ShaderService {

    private static final String VERTEX_FILE = "C:\\Users\\Belzee\\IdeaProjects\\graphics-project\\src\\main\\java\\engine\\shaders\\definitions\\vertexShader.txt";
    private static final String FRAGMENT_FILE = "C:\\Users\\Belzee\\IdeaProjects\\graphics-project\\src\\main\\java\\engine\\shaders\\definitions\\fragmentShader.txt";

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;

    public ShaderLoader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
    }

    @Override
    protected void getAllUniformLocations() {
        transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(transformationMatrixLocation, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(projectionMatrixLocation, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(viewMatrixLocation, viewMatrix);
    }
}
