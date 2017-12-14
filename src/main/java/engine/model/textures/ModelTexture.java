package engine.model.textures;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ModelTexture {
    private int textureId;

    private float shineDamper = 0.9f;
    private float reflectivity = 0.2f;

    private Boolean hasTransparency = false;
    private Boolean fakeLighting = false;

    public ModelTexture(int texture){
        this.textureId = texture;
    }
}
