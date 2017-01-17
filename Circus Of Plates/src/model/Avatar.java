package model;

import java.io.File;

import javafx.scene.image.Image;

public class Avatar {

	private static final String[] spriteName = { "render-1.png", "render-2.png" };
	private static final int STAND_HEIGHT = 400;

	private static int playerCount = 0;
	private Image spriteImage;
	private int x, y;

	public Avatar() {
		addSprite();
		y = STAND_HEIGHT;
		x = 0 + playerCount * 300;
	}

	private void addSprite() {
		spriteImage = new Image(new File("res" + File.separator + spriteName[playerCount]).toURI().toString());
		playerCount++;
	}

	public void move(int dist) {
		x += dist;
	}

	public Sprite getSprite() {
		return new SpriteAvatar(x, y, spriteImage);
	}
}
