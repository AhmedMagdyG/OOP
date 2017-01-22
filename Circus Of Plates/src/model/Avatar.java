package model;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class Avatar {

	private static final String[] spriteName = { "render-1.png", "render-2.png" };
	private static final int AVATAR_WIDTH = 90;

	private static int playerCount = 0;

	private Image spriteImage;
	private int x, y;
	private Stack leftStack, rightStack;

	public Avatar() {
		addSprite();
		y = Util.STAND_HEIGHT;
		x = 0 + playerCount * 300;
		leftStack = new Stack(playerCount);
		rightStack = new Stack(playerCount);
		calculateStackIndex();
	}

	private void calculateStackIndex() {
		leftStack.setX(x + AVATAR_WIDTH / 2 - Stack.WIDTH - Stack.RADIUS);
		rightStack.setX(x + Avatar.AVATAR_WIDTH - Stack.WIDTH + Stack.RADIUS + Stack.SHIFT);
	}

	private void addSprite() {
		spriteImage = new Image(new File("res" + File.separator + spriteName[playerCount]).toURI().toString());
		playerCount++;
	}

	public void move(int dist) {
		if (x + dist >= 0 && x + dist + AVATAR_WIDTH <= Util.SCENE_WIDTH) {
			x += dist;
		}
		calculateStackIndex();
	}

	public Sprite getAvatarSprite() {
		return new SpriteAvatar(x, y, spriteImage);
	}

	public Sprite getLeftStackSprite() {
		return leftStack.getSprite();
	}

	public Sprite getRightStackSprite() {
		return rightStack.getSprite();
	}

	public ArrayList<Sprite> getLeftStackShapes() {
		return leftStack.getShapesSprite();
	}

	public ArrayList<Sprite> getRightStackShapes() {
		return rightStack.getShapesSprite();
	}
}
