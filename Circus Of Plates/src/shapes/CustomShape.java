package shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class CustomShape extends Shape {

	protected Color color;
	protected int xPosition, yPosition;
	protected boolean availability;
	
	
	public CustomShape(Color color) {
		this.color = color;
	}
	
	public abstract Shape getShape();
	
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
	
}
