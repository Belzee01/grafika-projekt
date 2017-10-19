package engine.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ShaderService {

    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public ShaderService(String vertexFile, String fragmentFile) {
        this.vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        this.fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);

        this.programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);

        bindAttributes();
        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programId, attribute, variableName);
    }

    public void start() {
        GL20.glUseProgram(this.programId);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    private static int loadShader(String file, int type) {
        String shaderSource = loadFile(file);
        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, shaderSource);
        GL20.glCompileShader(shaderId);
        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderId, 500));
            System.out.println(String.format("Could not compile shader type : %s and path: %s", type, file));
        }
        return shaderId;
    }

    public void cleanUp() {
        stop();
        GL20.glDetachShader(programId, vertexShaderId);
        GL20.glDetachShader(programId, fragmentShaderId);
        GL20.glDeleteShader(vertexShaderId);
        GL20.glDeleteShader(fragmentShaderId);
        GL20.glDeleteProgram(programId);
    }


    private static String loadFile(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load the file.");
        }
    }
}