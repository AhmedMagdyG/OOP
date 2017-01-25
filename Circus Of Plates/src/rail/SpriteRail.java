package rail;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Sprite;

public class SpriteRail extends Sprite {

	private int xPosition, yPosition;
	private Image spriteImage;

	public SpriteRail(int x, int y, Image spriteImage) {
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
