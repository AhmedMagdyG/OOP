package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import model.Avatar;
import model.GameModel;
import model.ShapesPool;
import saving.JsonWriter;
import saving.StateBundle;
import shapes.CustomShape;
import sprite.Sprite;
import sprite.TextSprite;
import view.GraphicsDrawer;

public class GameController extends AnimationTimer {

	private final static int FIRST_AVATAR = 0, SECOND_AVATAR = 1;
	private final static int AVATAR_MOVED_DISTANCE = 15;
	private static final String[] gameEnd = { 
			"The First Player Won the game.",
			"The Second Player Won the game.",
			"The game end in tie." };

	private GraphicsDrawer graphicsDrawer;
	private GameModel gameModel;
	private AudioController audioController;

	private boolean gameEnded;
	private boolean avatarOneToleft, avatarOneToRight, avatarTwoToLeft, avatarTwoToRight;
	private int difficulty = 0;

	public GameController(GraphicsDrawer graphicsDrawer) {
		this.graphicsDrawer = graphicsDrawer;
		this.gameModel = new GameModel(difficulty);
		this.audioController = AudioController.getInstance();
		audioController.playBackgroundMusic();
		gameEnded = false;
	}

	public void resumeGame() {
		if (!gameEnded) {
			audioController.resumeBackgroundMusic();
			start();
		}
	}

	public void pauseGame() {
		audioController.pauseBackgroundMusic();
		stop();
	}

	public void newGame(int i) {
		if (i != difficulty) {
			difficulty = i;
			gameEnded = false;
			resumeGame();
			gameModel = new GameModel(difficulty);
		}
	}

	public void newGame() {
		gameEnded = false;
		resumeGame();
		gameModel = new GameModel(difficulty);
	}

	@Override
	public void handle(long now) {
		if (gameEnded)
			return;
		gameModel.moveShapes();
		handleMotion();
		ArrayList<Sprite> sprites = gameModel.getSprites();
		if (gameModel.gameEnded()) {
			int winner = gameModel.getWinner();
			pauseGame();
			gameEnded = true;
			sprites.add(new TextSprite(getWinMessage(winner)));
		}
		graphicsDrawer.draw(sprites);
	}

	private void handleMotion() {
		if (avatarOneToleft) {
			gameModel.movePlayer(FIRST_AVATAR, -AVATAR_MOVED_DISTANCE);
		}
		if (avatarOneToRight) {
			gameModel.movePlayer(FIRST_AVATAR, AVATAR_MOVED_DISTANCE);
		}
		if (avatarTwoToLeft) {
			gameModel.movePlayer(SECOND_AVATAR, -AVATAR_MOVED_DISTANCE);
		}
		if (avatarTwoToRight) {
			gameModel.movePlayer(SECOND_AVATAR, AVATAR_MOVED_DISTANCE);
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

	public void save(String filePath) {
		ArrayList<Avatar> avatars = gameModel.getAvatars();
		ShapesPool shapesPool = ShapesPool.getInstance();
		JsonWriter jsonWriter = new JsonWriter();
		StateBundle bundle = new StateBundle(avatars.get(FIRST_AVATAR),
				avatars.get(SECOND_AVATAR), shapesPool.getInUse());
		try {
			jsonWriter.save(bundle, filePath, "yarab");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getWinMessage(int winner) {
		if (winner > 0) {
			return gameEnd[0];
		} else if (winner < 0) {
			return gameEnd[1];
		} else {
			return gameEnd[2];
		}
	}

	public void load(String filePath) {
		StateBundle stateBundle = null;
		try {
			JsonWriter jsonWriter = new JsonWriter();
			stateBundle = jsonWriter.load(filePath);
		} catch(Exception e) {
			System.out.println("Yarab");
		}
		ArrayList<CustomShape> inUse = stateBundle.getInUse();
		ArrayList<Avatar> avatars = stateBundle.getAvatar();
		System.out.println(avatars.size());
	}
}