package controller;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import model.GameModel;
import model.Sprite;
import model.Util;
import view.GraphicsDrawer;

public class GameController extends AnimationTimer {

	private GraphicsDrawer graphicsDrawer;
	private GameModel gameModel;
	private ShapesController shapesController;
	private AudioController audioController;

	private boolean avatarOneToleft, avatarOneToRight, avatarTwoToLeft, avatarTwoToRight;
	private final static int FIRST_AVATAR = 0, SECOND_AVATAR = 1;
	private long prevCycleTime;

	public GameController(GraphicsDrawer graphicsDrawer, GameModel gameModel) {
		this.graphicsDrawer = graphicsDrawer;
		this.gameModel = gameModel;
		this.shapesController = new ShapesController(this);
		this.prevCycleTime = System.currentTimeMillis();
		this.audioController = AudioController.getInstance();
		audioController.playBackgroundMusic();
	}

	@Override
	public void handle(long now) {
		if (System.currentTimeMillis() - prevCycleTime > Util.SHAPE_CYCLE) {
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
			gameModel.movePlayer(FIRST_AVATAR, -Util.AVATAR_MOVED_DISTANCE);
		}
		if (avatarOneToRight) {
			gameModel.movePlayer(FIRST_AVATAR, Util.AVATAR_MOVED_DISTANCE);
		}
		if (avatarTwoToLeft) {
			gameModel.movePlayer(SECOND_AVATAR, -Util.AVATAR_MOVED_DISTANCE);
		}
		if (avatarTwoToRight) {
			gameModel.movePlayer(SECOND_AVATAR, Util.AVATAR_MOVED_DISTANCE);
		}
	}

	public void moveAvatars(KeyCode keyCode) {
		switch (keyCode) {
		case LEFT:
			avatarOneToleft = true;
			break;
		case RIGHT:
			avatarOneToRight = true;
			break;
		case A:
			avatarTwoToLeft = true;
			break;
		case D:
			avatarTwoToRight = true;
			break;
		default:
			break;
		}
	}

	public void stopMovingAvatars(KeyCode keyCode) {
		switch (keyCode) {
		case LEFT:
			avatarOneToleft = false;
			break;
		case RIGHT:
			avatarOneToRight = false;
			break;
		case A:
			avatarTwoToLeft = false;
			break;
		case D:
			avatarTwoToRight = false;
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
		audioController.pauseBackgroundMusic();
		stop();
	}

	public void resumeGame() {
		audioController.resumeBackgroundMusic();
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