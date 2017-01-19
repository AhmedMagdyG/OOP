package rail;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import model.Sprite;
import shapes.CustomShape;

public class Rail {
	private Allign direction;
	private int height, length, position;
	private List<CustomShape> shapes;
	private Image spriteImage;

	/**
	 * @param direction
	 *            the direction from which shapes start moving.
	 * @param length
	 *            the length of the rail.
	 * @param height
	 *            the y position of the rail.
	 * @param position
	 *            the position of the left side of the rail.
	 * @param spriteImage
	 *            The Image of the rail.
	 */
	public Rail(Allign direction, int position, int height, int length, int thickness, Image spriteImage) {
		this.direction = direction;
		this.height = height;
		this.length = length;
		this.position = position;
		this.spriteImage = spriteImage;
		this.shapes = new ArrayList<>();
	}

	/**
	 * @return the x position from which shapes start moving.
	 **/
	public int getStartX() {
		switch (direction) {
		case LEFT_ALLIGN:
			return this.position;
		default:
			return this.position + this.length;
		}
	}

	/**
	 * @return The x position from which shapes start falling.
	 */
	public int getEndX() {
		switch (direction) {
		case LEFT_ALLIGN:
			return this.length;
		default:
			return this.position;
		}
	}

	public int getHeight() {
		return this.height;
	}

	/**
	 * @param shape
	 *            The shape to be added to the rail.
	 */
	public void putShapeOnRail(CustomShape shape, double velocity) {
		if (this.direction == Allign.LEFT_ALLIGN) {
			shape.setXPostion(this.getStartX() - shape.getWidth());
			shape.setXVelocity(velocity);
		} else {
			shape.setXPostion(this.getStartX());
			shape.setXVelocity(-velocity);
		}
		shape.setYPostion(this.getHeight() - shape.getHeight());
		this.shapes.add(shape);
	}

	public void removeShape(int index) {
		shapes.remove(index);
	}

	public static boolean falling(Rail rail, CustomShape shape) {
		if (rail.direction == Allign.LEFT_ALLIGN) {
			return shape.getXPosition() >= rail.getEndX();
		} else {
			return shape.getXPosition() + shape.getWidth() <= rail.getEndX();
		}
	}

	public List<CustomShape> getShapes() {
		return this.shapes;
	}

	public Sprite getSprite() {
		return new SpriteRail(this.position, this.height, this.spriteImage);
	}
}
