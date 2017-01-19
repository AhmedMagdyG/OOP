package shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.Sprite;

public abstract class CustomShape {

	protected Color color;
	protected double xPosition, yPosition;
	protected double xVelocity;
	protected double yVelocity;

	public CustomShape(Color color) {
		this.color = color;
	}

	public abstract Shape getShape();

	public abstract Sprite getSprite();

	public void setXPostion(double xPosition) {
		this.xPosition = xPosition;
	}

	public void setYPostion(double yPosition) {
		this.yPosition = yPosition;
	}

	public double getXPosition() {
		return this.xPosition;
	}

	public double getYPosition() {
		return this.yPosition;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public void moveXDirection(double dx) {
		this.xPosition += dx;
	}

	public void moveYDirection(double dy) {
		this.yPosition += dy;
	}

	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public double getXVelocity() {
		return xVelocity;
	}

	public void resetMotion() {
		this.xPosition = 0;
		this.yPosition = 0;
		this.xVelocity = 0;
		this.yVelocity = 0;
	}

	public abstract int getWidth();

	public abstract int getHeight();
}
