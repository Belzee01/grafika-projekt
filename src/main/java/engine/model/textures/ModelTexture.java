package engine.model.textures;

import lombok.Getter;

public class ModelTexture {

    @Getter
    private int textureId;

    public ModelTexture(int textureId) {
        this.textureId = textureId;
    }

}
