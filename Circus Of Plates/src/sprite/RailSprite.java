package sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class RailSprite extends Sprite {

	private int xPosition, yPosition;
	private Image spriteImage;

	public RailSprite(int x, int y, Image spriteImage) {
		super();
		this.xPosition = x;
		this.yPosition = y;
		this.spriteImage = spriteImage;
	}

	@Override
	public void draw(GraphicsContext g) {
		g.drawImage(spriteImage, xPosition, yPosition);
	}
}
