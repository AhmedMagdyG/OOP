package shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.Sprite;

public abstract class CustomShape {

	protected Color color;
	protected int xPosition, yPosition;
	protected int xVelocity;
	protected int yVelocity;

	public CustomShape(Color color) {
		this.color = color;
	}

	public abstract Shape getShape();

	public abstract Sprite getSprite();

	public void setXPostion(int xPosition) {
		this.xPosition = xPosition;
	}

	public void setYPostion(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getXPosition() {
		return this.xPosition;
	}

	public int getYPosition() {
		return this.yPosition;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public void moveXDirection(int dx) {
		this.xPosition += dx;
	}

	public void moveYDirection(int dy) {
		this.yPosition += dy;
	}

	public void setXVelocity(int xVelocity) {
		this.xVelocity = xVelocity;
	}

	public void setYVelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}

	public int getYVelocity() {
		return yVelocity;
	}

	public int getXVelocity() {
		return xVelocity;
	}

	public abstract int getWidth();

	public abstract int getHeight();
}
