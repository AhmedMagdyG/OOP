package model;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;
import shapes.CustomShape;

public class Avatar {

	private static final String[] spriteName = { "render-1.png", "render-2.png" };
	private static final int AVATAR_WIDTH = 90;
	private static final int LEFT = 0, RIGHT = 1;

	private static int playerCount = 0;

	private Image spriteImage;
	private int x, y;
	private Stack[] stack;

	public Avatar() {
		addSprite();
		y = Util.STAND_HEIGHT;
		x = 0 + playerCount * 300;
		stack = new Stack[2];
		stack[LEFT] = new Stack(playerCount);
		stack[RIGHT] = new Stack(playerCount);
		calculateStackIndex();
	}

	private void calculateStackIndex() {
		stack[LEFT].setX(x + AVATAR_WIDTH / 2 - Stack.WIDTH - Stack.RADIUS);
		stack[RIGHT].setX(x + Avatar.AVATAR_WIDTH - Stack.WIDTH + Stack.RADIUS + Stack.SHIFT);
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

	public ArrayList<Sprite> getSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite> ();
		sprites.add(getAvatarSprite());
		sprites.addAll(getStackShapes());
		return sprites;
	}
	
	private Sprite getAvatarSprite() {
		return new SpriteAvatar(x, y, spriteImage);
	}

	private ArrayList<Sprite> getStackShapes() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for (Stack avatarStack : stack) {
			sprites.add(avatarStack.getSprite());
			sprites.addAll(avatarStack.getShapesSprite());
		}
		return sprites;
	}

	public boolean attach(CustomShape shape) {
		if(stack[LEFT].attach(shape) || stack[RIGHT].attach(shape)){
			return true;
		}
		return false;
	}
}
