package engine.terrain;

import java.util.Random;

public class HeightsGenerator {

    private static final float AMPLITUDE = 70f;

    private Random random = new Random();
    private int seed;

    public HeightsGenerator() {
        this.seed = random.nextInt(100000000);
    }

    public float generateHeight(int x, int z) {
        return 0.0f;
    }

}
