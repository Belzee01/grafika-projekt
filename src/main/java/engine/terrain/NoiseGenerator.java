package engine.terrain;

public abstract class NoiseGenerator {

    public abstract float getNoiseHeight(int x, int y);

    protected int seed;
    protected float roughness;
    protected int octaves;
    protected float amplitude;
}
