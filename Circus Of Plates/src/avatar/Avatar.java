package avatar;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;
import shapes.CustomShape;
import sprite.AvatarSprite;
import sprite.ScoreSprite;
import sprite.Sprite;
import view.GraphicsDrawer;

public class Avatar {

	private static final String[] spriteName = { "render-1.png", "render-2.png" };
	private static final int AVATAR_WIDTH = 90;
	private static final int LEFT = 0, RIGHT = 1;
	public static final int AVATAR_HEIGHT = 400;

	private static int playerCount = 0;

	private Image spriteImage;
	private int x, y;
	private Stack[] stack;
	private int playerIndex = 0;
	
	public Avatar() {
		playerIndex = playerCount % 2;
		y = AVATAR_HEIGHT;
		x = 300 + playerCount * 300;
		stack = new Stack[2];
		stack[LEFT] = new Stack(playerCount % 2);
		stack[RIGHT] = new Stack(playerCount % 2);
		calculateStackIndex();
		playerCount = (playerCount + 1) % 2;
		addSprite();
	}
	
	private void calculateStackIndex() {
		stack[LEFT].setX(x + AVATAR_WIDTH / 2 - Stack.WIDTH - Stack.RADIUS);
		stack[RIGHT].setX(x + Avatar.AVATAR_WIDTH - Stack.WIDTH + Stack.RADIUS + Stack.SHIFT);
	}

	private void addSprite() {
		spriteImage = new Image(new File("res" + File.separator + spriteName[playerCount]).toURI().toString());
	}

	public void move(int dist) {
		if (x + dist >= 0 && x + dist + AVATAR_WIDTH <= GraphicsDrawer.SCENE_WIDTH) {
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
		return new AvatarSprite(x, y, spriteImage);
	}

	private ArrayList<Sprite> getStackShapes() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for (Stack avatarStack : stack) {
			sprites.add(avatarStack.getSprite());
			sprites.addAll(avatarStack.getShapesSprite());
		}
		sprites.add(new ScoreSprite(playerIndex, getScore()));
		return sprites;
	}

	public int getScore(){
		int totalScore = 0;
		for(Stack avatarStack : stack){
			totalScore += avatarStack.getScore();
		}
		return totalScore;
	}
	
	public boolean attach(CustomShape shape) {
		if(stack[LEFT].attach(shape) || stack[RIGHT].attach(shape)){
			return true;
		}
		return false;
	}
	
	public Stack[] getStack() {
		return this.stack;
	}

	public void releaseShapes() {
		for(Stack stack : stack){
			stack.releaseShapes();
		}
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getPlayerIndex() {
		return this.playerIndex;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setIndex(int ind) {
		this.playerIndex = ind;
	}

	public void setStack(Stack[] s) {
		this.stack = s;
	}

	public boolean checkStackFull() {
		boolean stacksFull = true;
		for(Stack stack : stack){
			stacksFull = stacksFull && stack.checkStackFull();
		}
		return stacksFull;
	}
}
