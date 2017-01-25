package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import controller.ShapesController;
import javafx.scene.image.Image;
import rail.Rail;
import rail.RailsContainer;
import shapes.CustomShape;
import sprite.Sprite;

public class GameModel {
	private static final Logger LOGGER = Logger.getLogger(GameModel.class);

	private static final int PLAYER_COUNT = 2;
	private static final int EASY = 0, MEDIUM = 1, HARD = 2;

	private ArrayList<Avatar> avatars;
	private RailsContainer railsContainer;
	private ShapesController shapesController;
	private long prevCycleTime;
	private int shapeCycle;
	private int numberOfRails;

	public GameModel(int difficulty) {
		releaseAvatars();
		releaseShapes();
		this.prevCycleTime = System.currentTimeMillis();
		railsContainer = new RailsContainer();
		shapesController = new ShapesController(railsContainer);
		initialiseDifficulty(difficulty);
		initialiseAvatars();
		initialiseRails();
		LOGGER.info("Game Model Created");
	}

	public RailsContainer getRailsContainer() {
		return railsContainer;
	}

	public ArrayList<Avatar> getAvatars() {
		return this.avatars;
	}

	public void moveShapes() {
		if (System.currentTimeMillis() - prevCycleTime > shapeCycle) {
			shapesController.startNewShape();
			LOGGER.info("New Shape Started Moving on the rail");
			prevCycleTime = System.currentTimeMillis();
		}
		shapesController.moveShapes();
	}

	public void movePlayer(int i, int dist) {
		avatars.get(i).move(dist);
	}

	public ArrayList<Sprite> getSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for (Avatar avatar : avatars) {
			sprites.addAll(avatar.getSprites());
		}
		List<CustomShape> shapes = ShapesPool.getInstance().getInUse();
		for (int j = 0; j < shapes.size(); j++) {
			boolean attached = false;
			CustomShape shape = shapes.get(j);
			for (int i = 0; i < 2 && (!attached); i++) {
				attached = avatars.get(i).attach(shape);
			}
			if (attached) {
				ShapesPool.getInstance().removeInUse(shape);
				j--;
			} else {
				sprites.add(shape.getSprite());
			}
		}
		for (Rail rail : railsContainer.getRails()) {
			sprites.add(rail.getSprite());
		}
		LOGGER.debug("Sprites added to the List");
		return sprites;
	}

	private void initialiseDifficulty(int difficulty) {
		switch (difficulty) {
		case EASY:
			shapeCycle = 700;
			numberOfRails = 2;
			LOGGER.info("Difficulty is easy");
			break;
		case MEDIUM:
			shapeCycle = 500;
			numberOfRails = 2;
			LOGGER.info("Difficulty is medium");
			break;
		case HARD:
			shapeCycle = 500;
			numberOfRails = 4;
			LOGGER.info("Difficulty is hard");
			break;
		}
	}

	private void initialiseAvatars() {
		avatars = new ArrayList<Avatar>();
		for (int i = 0; i < PLAYER_COUNT; i++) {
			avatars.add(new Avatar());
		}
		LOGGER.info("Avatars Initialised");
	}

	private void initialiseRails() {
		Image spriteImage = new Image(new File(Rail.IMAGE).toURI().toString());
		for (int i = 0; i < numberOfRails; i++) {
			railsContainer
					.addRail(new Rail(Rail.allign[i], Rail.xPosition[i], Rail.yPosition[i], Rail.WIDTH, spriteImage));
		}
		LOGGER.info("rails Initialised");
	}

	private void releaseShapes() {
		ShapesPool.getInstance().releaseAll();
		LOGGER.debug("All shapes released");
	}

	private void releaseAvatars() {
		if (avatars == null) {
			return;
		}
		for (Avatar avatar : avatars) {
			avatar.releaseShapes();
		}
		LOGGER.debug("All shapes in avatars released");
	}

	public boolean gameEnded() {
		boolean stacksFull = true;
		for (Avatar avatar : avatars) {
			stacksFull = stacksFull && avatar.checkStackFull();
		}
		LOGGER.info("All stacks are full.. game ended");
		return stacksFull;
	}

	public int getWinner() {
		return avatars.get(0).getScore() - avatars.get(1).getScore();
	}
}
