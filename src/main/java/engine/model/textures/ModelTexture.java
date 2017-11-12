package engine.model.textures;

import lombok.Data;
import lombok.Getter;

@Data
public class ModelTexture {
    private int textureId;

    private float shineDamper = 1;
    private float reflectivity = 0;

    public ModelTexture(int texture){
        this.textureId = texture;
    }
}
