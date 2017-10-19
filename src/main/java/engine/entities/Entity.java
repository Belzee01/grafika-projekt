package engine.entities;

import engine.model.TexturedModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.lwjgl.util.vector.Vector3f;

@Data
@AllArgsConstructor
public class Entity {

    private TexturedModel texturedModel;
    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    public void updatePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void updateRotation(float rx, float ry, float rz) {
        this.rotation.x += rx;
        this.rotation.y += ry;
        this.rotation.z += rz;
    }
}
