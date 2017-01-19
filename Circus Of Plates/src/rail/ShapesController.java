package rail;

import java.util.Random;

import controller.GameController;
import gui.Main;
import shapes.CustomShape;

public class ShapesController {
	private GameController gameController;
	private static final int VELOCITY_MAX_VALUE = 20;
	private static final int VELOCITY_MIN_VALUE = 1;
	private static final int GRAVITY = 1;

	public ShapesController(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * @return true if the shape was added to a rail successfully and false if
	 *         there are no available shapes in the pool.
	 * @throws RuntimeException
	 *             if the rail set is empty.
	 */
	public boolean startNewShape() {
		CustomShape shapeToStart = gameController.getGameModel().getShapesPool().getObject();
		if (shapeToStart == null) {
			return false;
		}
		Rail rail = gameController.getGameModel().getRailsContainer().getRandomRail();
		if (rail == null) {
			throw new RuntimeException("Empty Rails set!");
		}
		int velocity = VELOCITY_MIN_VALUE + new Random().nextInt(VELOCITY_MAX_VALUE - VELOCITY_MIN_VALUE + 1);
		rail.putShapeOnRail(shapeToStart, velocity);
		return true;
	}

	/**
	 * Changes the shapes positions on the rails and while falling.
	 */
	public void moveShapes() {
		for (Rail rail : gameController.getGameModel().getRailsContainer().getRails()) {
			for (int index = 0; index < rail.getShapes().size(); index++) {
				CustomShape shape = rail.getShapes().get(index);
				shape.moveXDirection(shape.getXVelocity());
				if (Rail.falling(rail, shape)) {
					shape.setYVelocity(shape.getYVelocity() + GRAVITY);
					shape.moveYDirection(shape.getYVelocity());
				}
				if (shape.getYPosition() > Main.CANVAS_HEIGH) {
					rail.removeShape(index--);
				}
			}
		}
	}
}
