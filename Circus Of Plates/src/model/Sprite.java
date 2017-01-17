package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {
	
	protected int x, y;
	protected Image spriteImage;

	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract void draw(GraphicsContext g);
}
