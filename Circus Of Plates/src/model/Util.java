package model;

import java.util.Random;

public class Util {
	public static final Random RANDOM_GENERATOR = new Random();

	public static final int SCENE_WIDTH = 950, SCENE_HEIGH = 630;
	public static final int CANVAS_WIDTH = 950, CANVAS_HEIGH = 600;
	public static final int PLAYER_COUNT = 2;
	public static final int STAND_HEIGHT = 400;

	// GameController
	public final static int AVATAR_MOVED_DISTANCE = 15;
	public final static long SHAPE_CYCLE = 100;

	// ShapesController
	public static final int VELOCITY_MAX_VALUE = 10;
	public static final int VELOCITY_MIN_VALUE = 3;
	public static final double GRAVITY = 0.1;

	// ShapesPool
	public static final int POOL_SIZE = 50;
}
