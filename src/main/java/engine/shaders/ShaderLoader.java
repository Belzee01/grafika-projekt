package engine.shaders;

public class ShaderLoader extends ShaderService {

    private static final String VERTEX_FILE = "C:\\Users\\Belzee\\IdeaProjects\\graphics-project\\src\\main\\java\\engine\\shaders\\definitions\\vertexShader.txt";
    private static final String FRAGMENT_FILE = "C:\\Users\\Belzee\\IdeaProjects\\graphics-project\\src\\main\\java\\engine\\shaders\\definitions\\fragmentShader.txt";

    public ShaderLoader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
