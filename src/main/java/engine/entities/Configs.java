package engine.entities;

import org.lwjgl.util.vector.Vector3f;


public class Configs {

	public static final int FPS_CAP = 100;

	public static Vector3f LIGHT_POS = new Vector3f(0.3f, -1f, 0.5f);
	public static Vector3f LIGHT_COL = new Vector3f(1f, 0.8f, 0.8f);

	public static final int TERRAIN_SIZE = 100;

	public static final float AMPLITUDE = 70;
	public static final float ROUGHNESS = 0.35f;
	public static final int OCTAVES = 5;

}
