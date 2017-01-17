package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpriteAvatar extends Sprite {


	private Image spriteImage;
	
	public SpriteAvatar(int x, int y, Image spriteImage) {
		super(x, y);
		this.spriteImage = spriteImage;
	}

	public void draw(GraphicsContext g){
		g.drawImage(spriteImage, x, y);
	}

}
