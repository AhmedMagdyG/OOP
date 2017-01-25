package saving;

import javafx.scene.paint.Color;

public class FakeShape {

	private Color fillColor, strokeColor;
	private double xPos, yPos;
	private double xVel, yVel;
	private boolean isCaught;
	private int width, height;

	public FakeShape(Color fillColor, Color strokeColor, double xPos, double yPos, double xVel, double yVel,
			boolean isCaught, int width, int height) {
		this.fillColor = fillColor;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
		this.isCaught = isCaught;
		this.width = width;
		this.height = height;
	}
	
	public Color getFillColor() {
		return this.fillColor;
	}
	
	public Color getStrokeColor() {
		return this.strokeColor;
	}
	
	public double getXPos() {
		return this.xPos;
	}
	
	public double getYPos() {
		return this.yPos;
	}
	
	public double getXVel() {
		return this.xVel;
	}
	
	public double getYVel() {
		return this.yVel;
	}
	
	public boolean getIsCaught() {
		return this.isCaught;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}
