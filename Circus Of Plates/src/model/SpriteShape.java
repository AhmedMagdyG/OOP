package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SpriteShape extends Sprite {

	private int width, height;
	private Color color;
	
	public SpriteShape(int x, int y, int width, int height, Color color) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.color = color;
	}

	@Override
	public void draw(GraphicsContext g) {
		g.setFill(color);
		g.fillRect(super.x, super.y, width, height);
	}

}
