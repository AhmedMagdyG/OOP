package controller;

import model.Util;
import rail.Rail;
import shapes.CustomShape;

public class ShapesController {
	private GameController gameController;

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
		double velocity = Util.VELOCITY_MIN_VALUE
				+ Util.RANDOM_GENERATOR.nextDouble() * (Util.VELOCITY_MAX_VALUE - Util.VELOCITY_MIN_VALUE + 1);
		shapeToStart.resetMotion();
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
					shape.setYVelocity(shape.getYVelocity() + Util.GRAVITY);
					shape.moveYDirection(shape.getYVelocity());
				}
				if (shape.getYPosition() > Util.CANVAS_HEIGH) {
					gameController.getGameModel().getShapesPool().releaseShape(rail.getShapes().get(index));
					rail.removeShape(index--);
				}
			}
		}
	}
}
