package controller;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import model.GameModel;
import model.Sprite;
import view.GraphicsDrawer;

public class GameController extends AnimationTimer {

	private GraphicsDrawer graphicsDrawer;
	private GameModel gameModel;
	private ShapesController shapesController;
	private AudioController audioController;

	private boolean avatarOneToleft, avatarOneToRight, avatarTwoToLeft, avatarTwoToRight;
	private final static int FIRST_AVATAR = 0, SECOND_AVATAR = 1;
	private final static int MOVED_DISTANCE = 5;
	private final static long SHAPE_CYCLE = 500;
	private long prevCycleTime;

	public GameController(GraphicsDrawer graphicsDrawer, GameModel gameModel) {
		this.graphicsDrawer = graphicsDrawer;
		this.gameModel = gameModel;
		this.shapesController = new ShapesController(this);
		this.prevCycleTime = System.currentTimeMillis();
		this.audioController = new AudioController();
		audioController.playBackgroundMusic();
	}

	@Override
	public void handle(long now) {
		if (System.currentTimeMillis() - prevCycleTime > SHAPE_CYCLE) {
			shapesController.startNewShape();
			prevCycleTime = System.currentTimeMillis();
		}
		shapesController.moveShapes();
		handleMotion();
		gameModel.updateData();
		ArrayList<Sprite> sprites = gameModel.getSprites();
		graphicsDrawer.draw(sprites);
	}

	private void handleMotion() {
		if (avatarOneToleft) {
			gameModel.movePlayer(FIRST_AVATAR, -MOVED_DISTANCE);
			avatarOneToleft = false;
		}
		if (avatarOneToRight) {
			gameModel.movePlayer(FIRST_AVATAR, MOVED_DISTANCE);
			avatarOneToRight = false;
		}
		if (avatarTwoToLeft) {
			gameModel.movePlayer(SECOND_AVATAR, -MOVED_DISTANCE);
			avatarTwoToLeft = false;
		}
		if (avatarTwoToRight) {
			gameModel.movePlayer(SECOND_AVATAR, MOVED_DISTANCE);
			avatarTwoToRight = false;
		}
	}

	public void moveAvatars(String direction) {
		switch (direction) {
		case "LEFT":
			avatarOneToleft = true;
			break;
		case "RIGHT":
			avatarOneToRight = true;
			break;
		case "A":
			avatarTwoToLeft = true;
			break;
		case "D":
			avatarTwoToRight = true;
			break;
		default:
			break;
		}
	}

	public void newGame() {
		System.out.println("NEW GAME CLICk");
	}

	public void save() {
		System.out.println("SAVE GAME");
	}

	public void quit() {
		// TODO Auto-generated method stub

	}

	public void pauseGame() {
		stop();
	}

	public void resumeGame() {
		start();
	}

	public void load() {
		// TODO Auto-generated method stub

	}

	public void ChangeSettings() {
		// TODO Auto-generated method stub

	}

	public GameModel getGameModel() {
		return gameModel;
	}

}