package engine.model;

import engine.model.textures.ModelTexture;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TexturedModel {

    private RawModel rawModel;
    private ModelTexture texture;

}
