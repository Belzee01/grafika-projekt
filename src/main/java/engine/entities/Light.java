package engine.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.util.vector.Vector3f;

@AllArgsConstructor
public class Light {

    @Getter @Setter
    private Vector3f position;
    @Getter @Setter
    private Vector3f colour;
}
