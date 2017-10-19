package engine.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class ShaderLoader extends ShaderService {

    private static final String VERTEX_FILE = "C:\\Users\\Belzee\\IdeaProjects\\graphics-project\\src\\main\\java\\engine\\shaders\\definitions\\vertexShader.txt";
    private static final String FRAGMENT_FILE = "C:\\Users\\Belzee\\IdeaProjects\\graphics-project\\src\\main\\java\\engine\\shaders\\definitions\\fragmentShader.txt";

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;

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
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(transformationMatrixLocation, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(projectionMatrixLocation, matrix);
    }
}
