package controller;

import java.util.Random;

import model.ShapesPool;
import rail.Rail;
import rail.RailsContainer;
import shapes.CustomShape;
import view.GraphicsDrawer;

public class ShapesController {
	
	public static final int VELOCITY_MAX_VALUE = 7;
	public static final int VELOCITY_MIN_VALUE = 3;
	public static final double GRAVITY = 0.05;
	
	private RailsContainer railsContainer;

	public ShapesController(RailsContainer railsContainer) {
		this.railsContainer = railsContainer;
	}

	/**
	 * @return true if the shape was added to a rail successfully and false if
	 *         there are no available shapes in the pool.
	 * @throws RuntimeException
	 *             if the rail set is empty.
	 */
	public boolean startNewShape() {
		CustomShape shapeToStart = ShapesPool.getInstance().getObject();
		if (shapeToStart == null) {
			return false;
		}
		Rail rail = railsContainer.getRandomRail();
		if (rail == null) {
			throw new RuntimeException("Empty Rails set!");
		}
		double velocity = VELOCITY_MIN_VALUE
				+ (new Random()).nextDouble() * (VELOCITY_MAX_VALUE - VELOCITY_MIN_VALUE + 1);
		shapeToStart.resetMotion();
		rail.putShapeOnRail(shapeToStart, velocity);
		return true;
	}

	/**
	 * Changes the shapes positions on the rails and while falling.
	 */
	public void moveShapes() {
		for (Rail rail : railsContainer.getRails()) {
			for (int index = 0; index < rail.getShapes().size(); index++) {
				CustomShape shape = rail.getShapes().get(index);
				shape.moveXDirection(shape.getXVelocity());
				if (Rail.falling(rail, shape)) {
					shape.setYVelocity(shape.getYVelocity() + GRAVITY);
					shape.moveYDirection(shape.getYVelocity());
				}
				if (shape.getYPosition() > GraphicsDrawer.CANVAS_HEIGH) {
					ShapesPool.getInstance().releaseShape(rail.getShapes().get(index));
					rail.removeShape(index--);
				}
			}
		}
	}
}
