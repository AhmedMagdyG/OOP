package controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

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
	private static final Logger LOGGER = Logger.getLogger(GameController.class);

	private final static int FIRST_AVATAR = 0, SECOND_AVATAR = 1;
	private final static int AVATAR_MOVED_DISTANCE = 15;
	private static final String[] gameEnd = { "The First Player Won the game.", "The Second Player Won the game.",
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
			LOGGER.info("Game resumed");
		}
	}

	public void pauseGame() {
		audioController.pauseBackgroundMusic();
		stop();
		LOGGER.info("Game paused");
	}

	public void newGame(int i) {
		if (i != difficulty) {
			difficulty = i;
			gameEnded = false;
			resumeGame();
			gameModel = new GameModel(difficulty);
		}
		LOGGER.info("New game started");
	}

	public void newGame() {
		gameEnded = false;
		resumeGame();
		gameModel = new GameModel(difficulty);
	}

	@Override
	public void handle(long now) {
		if (gameEnded) {
			return;
		}
		gameModel.moveShapes();
		handleMotion();
		ArrayList<Sprite> sprites = gameModel.getSprites();
		if (gameModel.gameEnded()) {
			int winner = gameModel.getWinner();
			pauseGame();
			gameEnded = true;
			sprites.add(new TextSprite(getWinMessage(winner)));
			LOGGER.debug("Winner = " + winner);
			LOGGER.info(getWinMessage(winner));
		}
		graphicsDrawer.draw(sprites);
	}

	private void handleMotion() {
		if (avatarOneToleft) {
			gameModel.movePlayer(FIRST_AVATAR, -AVATAR_MOVED_DISTANCE);
			LOGGER.info("First player moved to left");
		}
		if (avatarOneToRight) {
			gameModel.movePlayer(FIRST_AVATAR, AVATAR_MOVED_DISTANCE);
			LOGGER.info("First player moved to right");
		}
		if (avatarTwoToLeft) {
			gameModel.movePlayer(SECOND_AVATAR, -AVATAR_MOVED_DISTANCE);
			LOGGER.info("Second player moved to left");
		}
		if (avatarTwoToRight) {
			gameModel.movePlayer(SECOND_AVATAR, AVATAR_MOVED_DISTANCE);
			LOGGER.info("Second player moved to right");
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
		StateBundle bundle = new StateBundle(avatars.get(FIRST_AVATAR), avatars.get(SECOND_AVATAR),
				shapesPool.getInUse());
		try {
			jsonWriter.save(bundle, filePath, "yarab");
			LOGGER.info("Game saved successfully");
		} catch (Exception e) {
			LOGGER.error("Saving Failed");
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
			LOGGER.info("Game loaded successfully");
		} catch (Exception e) {
			LOGGER.fatal("Game loading failed.");
		}
		ArrayList<CustomShape> inUse = stateBundle.getInUse();
		ArrayList<Avatar> avatars = stateBundle.getAvatar();
		LOGGER.debug("Number of avatars = " + avatars.size());
	}
}