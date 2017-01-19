package rail;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Sprite;

public class SpriteRail extends Sprite {

	private Image spriteImage;

	public SpriteRail(int x, int y, Image spriteImage) {
		super(x, y);
		this.spriteImage = spriteImage;
	}

	@Override
	public void draw(GraphicsContext g) {
		g.drawImage(spriteImage, x, y);
	}

}
