package engine.model.loaders;

import engine.model.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OBJLoader {

    public static RawModel loadObjModel(String fileName, Loader loader) {

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indicies = new ArrayList<>();

        float[] verticesArray = null;
        final float[][] normalsArray = {null};
        final float[][] textureArray = {null};
        int[] indicesArray = null;

        try (Stream<String> stream = Files.lines(Paths.get("C:\\Users\\Kajetan\\IntelliJProjects\\grafika-projekt\\src\\main\\resources\\" + fileName + ".obj"))) {

            stream.forEach(line -> {
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                } else if (line.startsWith("vt ")) {
                    Vector2f texture = new Vector2f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if (line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                } else if (line.startsWith("f ")) {
                    if (textureArray[0] == null || normalsArray[0] == null) {
                        textureArray[0] = new float[vertices.size() * 2];
                        normalsArray[0] = new float[vertices.size() * 3];
                    }

                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");

                    processVertex(vertex1, indicies, textures, normals, textureArray[0], normalsArray[0]);
                    processVertex(vertex2, indicies, textures, normals, textureArray[0], normalsArray[0]);
                    processVertex(vertex3, indicies, textures, normals, textureArray[0], normalsArray[0]);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indicies.size()];

        int vertexCounter = 0;
        for (Vector3f vec : vertices) {
            verticesArray[vertexCounter++] = vec.x;
            verticesArray[vertexCounter++] = vec.y;
            verticesArray[vertexCounter++] = vec.z;
        }

        int indicesCounter = 0;
        for (Integer i : indicies) {
            indicesArray[indicesCounter++] = i;
        }

        return loader.loadToVAO(verticesArray, textureArray[0], normalsArray[0], indicesArray);
    }

    private static void processVertex(
            String[] vertexData,
            List<Integer> indices,
            List<Vector2f> textures,
            List<Vector3f> normals,
            float[] textureArray,
            float[] normalsArray
    ) {

        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);

        Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer * 2] = currentTexture.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTexture.y;

        Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNormal.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNormal.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNormal.z;
    }

}
