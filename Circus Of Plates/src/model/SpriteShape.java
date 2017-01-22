package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SpriteShape extends Sprite {

	private int width, height;
	private Color fillColor, strokeColor;

	public SpriteShape(double x, double y, int width, int height, Color fillColor) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.fillColor = fillColor;
	}

	public SpriteShape(double x, double y, int width, int height, Color fillColor, Color strokeColor) {
		this(x, y, width, height, fillColor);
		this.strokeColor = strokeColor;
	}

	@Override
	public void draw(GraphicsContext g) {
		g.setFill(fillColor);
		g.fillRect(super.x, super.y, width, height);
		if (strokeColor != null) {
			g.setStroke(strokeColor);
			g.strokeRect(super.x, super.y, width, height);
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}
}
